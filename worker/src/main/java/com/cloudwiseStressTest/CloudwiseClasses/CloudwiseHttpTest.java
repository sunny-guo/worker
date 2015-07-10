package com.cloudwiseStressTest.CloudwiseClasses;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;























import com.cloudwiseStressTest.Assertion.SizeAssertion;
//import com.cloudwiseStressTest.Assertion.SizeAssertion;
import com.cloudwiseStressTest.CloudwiseInterfaces.ICloudwiseTest;
import com.cloudwiseStressTest.CloudwiseInterfaces.IParametersManager;
//import com.cloudwiseStressTest.CloudwiseInterfaces.IProcessManager;
import com.cloudwiseStressTest.CloudwiseInterfaces.ITestResult;
import com.cloudwiseStressTest.ManagersImpl.ParametersManager;
import com.cloudwiseStressTest.PostProcessors.RegexPostProcessor;
import com.cloudwiseStressTest.ResultManager.HttpTestResult;
//import com.cloudwiseStressTest.ManagersImpl.ProcessManager;
//import com.cloudwiseStressTest.ManagersImpl.RequestMaker;
//import com.cloudwiseStressTest.PostProcessors.RegexPostProcessor;
import com.cloudwiseStressTest.ResultManager.SingleHttpStepResult;
//import com.cloudwiseStressTest.Util.Utils;
import com.google.code.http4j.Client;
import com.google.code.http4j.CookieCache;
import com.google.code.http4j.Header;
import com.google.code.http4j.Headers;
import com.google.code.http4j.Response;
import com.google.code.http4j.impl.BasicClient;
import com.google.code.http4j.impl.CanonicalHeader;
import com.google.code.http4j.impl.CookieStoreAdapter;
import com.google.code.http4j.utils.DateUtil;
import com.google.code.http4j.utils.Metrics;
import com.google.code.http4j.utils.ThreadLocalMetricsRecorder;

public class CloudwiseHttpTest implements ICloudwiseTest{
	private String testName;
	private JSONObject source;
//	private CookieCache cache;
	
	public CloudwiseHttpTest(JSONObject source){
		this.source = source;
		this.testName = source.get("testName") != null?
				source.getString("testName"):"test"+System.currentTimeMillis();
	}
	
	/* (non-Javadoc)
	 * @see com.cloudwiseStressTest.CloudwiseInterfaces.CloudwiseTest#getTestName()
	 */
	public String getTestName() {
		return testName;
	}
	
	/* (non-Javadoc)
	 * @see com.cloudwiseStressTest.CloudwiseInterfaces.CloudwiseTest#run()
	 */
	public JSONObject run(){
		ITestResult result = new HttpTestResult();
		result.setTestName(source.getString("testName"));
		JSONArray params = source.getJSONArray("parameters");
		IParametersManager parameters = new ParametersManager();
		parameters.setupParamsMap(params);
		JSONArray stepsArray = source.getJSONArray("steps");
		Client client = new BasicClient();
		client.useConnectionPool(true);
		client.setMaxConnectionsPerHost(2);
//		cache = client.getCookieCache();
		Response response;
		JSONObject header;
		for(int i=0;i<stepsArray.size();i++){
			JSONObject step = stepsArray.getJSONObject(i);	
			if(step.get("header")!=null){
				header = step.getJSONObject("header");
				header = updateParamsToValue(header);
			}else{
				header = new JSONObject();
			}
			
			 URI uri;
			try {
				uri = new URL(step.getString("url")).toURI();
			
//			if(step.containsKey("cookieSet")&&step.getString("cookieSet").equals("YES")){
////				setCookie(header, cache.get(uri));
//			}
			
			client.addHearderJson(header);
			if(step.get("parameters")!=null){
				JSONObject query = step.getJSONObject("parameters");
				query = updateParamsToValue(query);
				client.addQueryJson(query);
			}
			
			  
			if(step.getString("method").equals("get")){
				response = client.get(step.getString("url"));
			}else{
				response = client.post(step.getString("url"));
	
			}	
			  Metrics metrics = response.getMetrics();
			  DecimalFormat df = new DecimalFormat("0.000");
			  SingleHttpStepResult single = new SingleHttpStepResult();
			  single.setRequestHeader(headerStringToJson(ThreadLocalMetricsRecorder.getInstance().getReq(),1));
			  single.setResponseHeader(headerStringToJson(ThreadLocalMetricsRecorder.getInstance().getRes(),2));
//			  single.setRequestHeader(ThreadLocalMetricsRecorder.getInstance().getReq());//header 字符串格式
//			  single.setResponseHeader(ThreadLocalMetricsRecorder.getInstance().getRes());
			  single.setAbsoluteEndTime(metrics.getReceivingEndTime()/1000000);
			  single.setBody(new String(response.getEntity()));
			  single.setStatus(ThreadLocalMetricsRecorder.getInstance().getStatus());
			  JSONObject size = new JSONObject();
			  size.put("uploadSize", metrics.getBytesSent());
			  size.put("downloadSize", metrics.getBytesReceived());
			  single.setSize(size);
			  JSONObject time = new JSONObject();
			  time.put("blockingCost", df.format(metrics.getBlockingCost()/1000000.0));
			  time.put("dnsCost", df.format(metrics.getDnsLookupCost()/1000000.0));
			  time.put("connectingCost", df.format(metrics.getConnectingCost()/1000000.0));
			  time.put("sendCost", df.format(metrics.getSendingCost()/1000000.0));
			  time.put("waitCost", df.format(metrics.getWaitingCost()/1000000.0));
			  time.put("receiveCost", df.format(metrics.getReceivingCost()/1000000.0));
			  if(step.getString("protocol").equals("https")){
				  time.put("sslCost", metrics.getSslHandshakeCost()/1000000);  
			  }
			  single.setTime(time);
//			  if(step.containsKey("cookieSave")){
//					saveCookie(single.getResponseHeader().getString("Set-Cookie"),
//							"src/main/java/com/cloudwiseStressTest/resource/cookies/"+
//					step.getString("cookieSave")+".txt");
//				}
			  
//			  if(step.containsKey("cookieSave")&&step.getString("cookieSave").equals("YES")){
////					saveCookie(single.getResponseHeader().getString("Set-Cookie"),
////							"src/main/java/com/cloudwiseStressTest/resource/cookies/"+
////					step.getString("cookieSave")+".txt");
//				
//				 List<Header> headers = new LinkedList<Header>();
//					headers.add(new CanonicalHeader(Headers.REQUEST_COOKIE,  single.getResponseHeader().getString("Set-Cookie")));
////					headers.add(new CanonicalHeader(Headers.RESPONSE_COOKIE, "NID=38=g4PriUXNBibltyAS5Rko6b6ygubtZQs0s2FAy0zMYHOKOIY5rQyE1gAskYS0MU6g767OB24_xknddTgcBKuEMsShbEhZMH0ev9A_uMbWF9D61d8VcASWeksFx2kTSR_i; expires=Fri, 11-Mar-2011 12:20:54 GMT; path=/; domain=.google.com; HttpOnly"));
////					headers.add(new CanonicalHeader(Headers.CONTENT_LENGTH, "458"));
//				 cache.set(uri, headers);
//				}
//			  
			if(step.containsKey("postProcessors")){
				dealWithPostProcessors(single,step.getJSONArray("postProcessors"));
			}
			  result.putSubResult(step.getString("description"), single.generateResultToTransport());
			  System.out.println(result.getHttpTestResult());
			  } catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
		 }
		return result.getHttpTestResult();
	}
	
	
	/** 
	* @Description: 将公共参数转化为实值
	* @author jasonlee
	* @date 2015年7月8日
	*/
	private JSONObject updateParamsToValue(JSONObject params){
		JSONObject valueJson = new JSONObject();
		 Iterator keys = params.keys();
		 while(keys.hasNext()){			 				
				 String key = keys.next().toString();	
				 String value = params.getString(key);	
				 if(value.substring(0, 1).equals("$")){
						ParametersManager pm = new ParametersManager();
						value =pm.getValueBykey(value);
					}
				 valueJson.put(key, value);
		 }
		 return valueJson;
	}
		
//	private void saveCookie(String cookieContent,String path){
//		try {
//			File newTextFile = new File(path);
//			FileWriter fw;
//			fw = new FileWriter(newTextFile);
//			fw.write(cookieContent);
//			fw.close();
//			} catch (IOException e) {
//				 // TODO Auto-generated catch block
//				 e.printStackTrace();
//			}
//	}
	
	private void setCookie(JSONObject header,String cookie){
			header.put("Cookie", cookie);
	}
	
//	private void setCookie(JSONObject header,String path){
//		File file = new File(path);
//        FileReader reader;
//		try {
//			reader = new FileReader(file);
//			 int fileLen = (int)file.length();
//		     char[] chars = new char[fileLen];
//			reader.read(chars);
//			String txt = String.valueOf(chars); 
//			 txt = txt.substring(0, txt.indexOf(";"));
//			header.put("Cookie", txt);
//		}catch(Exception e){
//			
//		}
//	}
	
	/* 
	* @Description: 将header 转化为jsonObject对象
	* @param headerString 
	* @param type 1-requestHeader;2-responseHeader
	* @author jasonlee
	* @date 2015年6月30日
	*/
	private JSONObject headerStringToJson(String headerString,int type){
		JSONObject headerObj = new JSONObject();
//		headerString = headerString.substring(2,headerString.length()-1);
		headerString=headerString.replace("\r",""); 
		String matchStr = "\n";
		String[] headers = headerString.split(matchStr);
//		if(type == 1){								//是否显示header的第一行
//			headerObj.put("request", headers[0]);
//		}else if(type==2){
//			headerObj.put("response", headers[0]);
//		}
		for(int i=1;i<headers.length;i++){
			String header = headers[i];
			if(header.contains(":")){
				String[] details = header.split(":",2);
				headerObj.put(details[0],details[1]);
			}
		}
		return headerObj;
	}
	
	/* (non-Javadoc)
	 * @see com.cloudwiseStressTest.CloudwiseInterfaces.ICloudwiseTest#dealWithPostProcessors(com.
	 * cloudwiseStressTest.ResultManager.SingleHttpStepResult, net.sf.json.JSONArray)
	 */
	public void dealWithPostProcessors(SingleHttpStepResult single,JSONArray postProcessors){
		for(int i=0;i<postProcessors.size();i++){
			switch(postProcessors.getJSONObject(i).getString("type")){
			case "assertions":
				dealWithAssertions(single,postProcessors.getJSONObject(i).getJSONArray("assertions"));break;
			case "RegexExtractor":
				RegexPostProcessor regex = new RegexPostProcessor(postProcessors.getJSONObject(i).getString("refname"),
						postProcessors.getJSONObject(i).getString("regex"), single, 
						postProcessors.getJSONObject(i).getInt("regexNum"));
				regex.dealWithPostProcessor();
				break;
				default:break;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.cloudwiseStressTest.CloudwiseInterfaces.ICloudwiseTest#dealWithAssertions(com.cloudwiseStressTest
	 * .ResultManager.SingleHttpStepResult, net.sf.json.JSONArray)
	 */
	public void dealWithAssertions(SingleHttpStepResult single,JSONArray assertions){
		for(int i=0;i<assertions.size();i++){
			if(assertions.getJSONObject(i).getString("type").equals("sizeAssertion")){
				SizeAssertion sizeAssertion = new SizeAssertion(single.getSize().getInt("downloadSize"),
						assertions.getJSONObject(i).getString("mode"),
						assertions.getJSONObject(i).getInt("size"));
				sizeAssertion.dealWithAssertion();
				single.getAssertions().add(sizeAssertion.getAssertionResult());
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.cloudwiseStressTest.CloudwiseInterfaces.CloudwiseTest#stop()
	 */
	public void stop(){
		
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}
	
	public JSONObject getSource() {
		return source;
	}

	public void setSource(JSONObject source) {
		this.source = source;
	}

}
