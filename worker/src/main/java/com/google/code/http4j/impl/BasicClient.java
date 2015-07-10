/**
 * Copyright (C) 2010 Zhang, Guilin <guilin.zhang@hotmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.http4j.impl;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.http4j.Client;
import com.google.code.http4j.ConnectionManager;
import com.google.code.http4j.CookieCache;
import com.google.code.http4j.DNS;
import com.google.code.http4j.Request;
import com.google.code.http4j.RequestExecutor;
import com.google.code.http4j.Response;
import com.google.code.http4j.impl.conn.ConnectionPool;
import com.google.code.http4j.impl.conn.SingleConnectionManager;
import com.google.code.http4j.utils.Metrics;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class BasicClient implements Client {

	private static final Logger LOGGER = LoggerFactory.getLogger(BasicClient.class);
	
	protected ConnectionManager connectionManager;

	protected final CookieCache cookieCache;

	public CookieCache getCookieCache() {
		return cookieCache;
	}

	protected final ResponseParser responseParser;

	protected boolean followRedirect;
	
	protected JSONObject headerArray;
	
	protected JSONObject queryArray;
	
	protected String cookie;

	public void addHearderJson(JSONObject headerArray){
		this.headerArray = headerArray;
	}
	
	public void addQueryJson(JSONObject queryArray){
		this.queryArray = queryArray;
	}
	
	public BasicClient() {
		cookieCache = createCookieCache();
		responseParser = createResponseParser();
		useDNSCache(true);
		useConnectionPool(true);
		followRedirect = false;
	}

	@Override
	public BasicClient useConnectionPool(boolean use) {
		if (null != connectionManager) {
			connectionManager.shutdown();
		}
		connectionManager = use ? new ConnectionPool() : new SingleConnectionManager();
		return this;
	}

	@Override
	public BasicClient useDNSCache(boolean use) {
		DNS.useCache(use);
		return this;
	}

	@Override
	public Client followRedirect(boolean follow) {
		this.followRedirect = follow;
		return this;
	}

	@Override
	public Response get(String url) throws InterruptedException, IOException,
			URISyntaxException {
		Get get = new Get(url);
		if(queryArray != null){
			 Set<String> keys = queryArray.keySet();
		        for(String key: keys){
		             String value = (String) queryArray.get(key);
		            get.addParameter(key, value);
		        }
		}
		
		if(headerArray != null){
				 Set<String> keys = headerArray.keySet();
			        for(String key: keys){
			             String value = (String) headerArray.get(key);
			            get.setHeader(key, value);
			}
		}
		return submit(get);
	}

//	   /**
//     * 将JSONObjec对象转换成Map-List集合
//     * @see JSONHelper#reflect(JSONArray)
//     * @param json
//     * @return
//     */
//    public static HashMap<String, Object> reflect(JSONObject json){
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        Set<?> keys = json.keySet();
//        for(Object key : keys){
//            Object o = json.get(key);
//            if(o instanceof JSONArray)
//                map.put((String) key, reflect((JSONArray) o));
//            else if(o instanceof JSONObject)
//                map.put((String) key, reflect((JSONObject) o));
//            else
//                map.put((String) key, o);
//        }
//        return map;
//    }
//	
	@Override
	public Response post(String url) throws InterruptedException, IOException,
			URISyntaxException {
		Post post = new Post(url);
		
		if(headerArray != null){
				 Set<String> keys = headerArray.keySet();
			        for(String key: keys){
			             String value = (String) headerArray.get(key);
			             post.setHeader(key, value);
			}
		}
		
		if(queryArray != null){
			 Set<String> keys = queryArray.keySet();
		        for(String key: keys){
		             String value = (String) queryArray.get(key);
		             post.addParameter(key, value);
		        }
		}
		return submit(post);
	}

	@Override
	public Response submit(Request request) throws InterruptedException,
			IOException, URISyntaxException {
		return submit(request, null);
	}
	
	@Override
	public void shutdown() {
		cookieCache.clear();
		connectionManager.shutdown();
	}
	
	@Override
	public Client setMaxConnectionsPerHost(int max) {
		connectionManager.setMaxConnectionsPerHost(max);
		return this;
	}
	
	protected Response submit(Request request, Metrics parentMetrics) throws InterruptedException,
			IOException, URISyntaxException {
		RequestExecutor executor = new BasicRequestExecutor(connectionManager, cookieCache, responseParser);
		Response response = executor.execute(request);
//		LOGGER.debug("Metrics for {} : \r\n{}", request.getURI(), response.getMetrics());
		response.getMetrics().setParentMetrics(parentMetrics);
		return postProcess(request, response);
	}
	
	protected Response redirect(String url, Metrics parentMetrics) throws InterruptedException, IOException,
			URISyntaxException {
		return submit(new Get(url), parentMetrics);
	}

	protected Response postProcess(Request request, Response response)
			throws InterruptedException, IOException, URISyntaxException {
		if (followRedirect && response.needRedirect()) {
			String location = getLocation(request.getURI(), response.getRedirectLocation());
			response = redirect(location, response.getMetrics());
		}
		return response;
	}

	protected ResponseParser createResponseParser() {
		return new ResponseParser();
	}

	protected CookieCache createCookieCache() {
		return new CookieStoreAdapter();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			shutdown();
		} finally {
			super.finalize();
		}
	}
	
	protected String getLocation(URI uri, String location) {
		return location.startsWith("http") ? location : new StringBuilder(
				uri.getScheme()).append("://").append(uri.getAuthority())
				.append(location).toString();
	}
}
