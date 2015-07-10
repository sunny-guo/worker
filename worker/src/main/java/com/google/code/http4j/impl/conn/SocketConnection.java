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

package com.google.code.http4j.impl.conn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.zip.GZIPInputStream;

import com.google.code.http4j.Connection;
import com.google.code.http4j.DNS;
import com.google.code.http4j.Host;
import com.google.code.http4j.utils.IOUtils;
import com.google.code.http4j.utils.ThreadLocalMetricsRecorder;
import com.google.code.http4j.utils.Timer;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class SocketConnection implements Connection {

	protected Socket socket;

	protected Host host;

	protected int timeout;

	protected boolean reusable;

	public SocketConnection(Host host) throws IOException {
		this(host, 0);
	}

	public SocketConnection(Host host, int timeout) throws IOException {
		this.host = host;
		this.timeout = timeout;
		this.reusable = true;
		socket = createSocket();
	}

	@Override
	public void close() throws IOException {
		IOUtils.close(socket);
	}

	protected Socket createSocket() throws IOException {
		return new Socket();
	}

	@Override
	public final void connect() throws IOException {
		doConnect();
	}

	protected void doConnect() throws IOException {
		SocketAddress address = getSocketAddress(host);
		Timer timer = ThreadLocalMetricsRecorder.getInstance().getConnectionTimer();
		timer.start();
//		System.out.println("链接开始时间："+System.currentTimeMillis());
		socket.connect(address, timeout);
//		System.out.println("链接结束时间："+System.currentTimeMillis());
		timer.stop();
		socket.setSoLinger(true, 60);
	}

	protected SocketAddress getSocketAddress(Host host)
			throws UnknownHostException {
		int port = (host.getPort() < 0) ? host.getDefaultPort() : host.getPort();
		InetAddress address = DNS.getAddress(host.getName());
		return new InetSocketAddress(address, port);
	}

	@Override
	public boolean isClosed() {
		return socket.isClosed();
	}

	@Override
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public Host getHost() {
		return host;
	}
	
	@Override
	public void setReusable(boolean reusable) {
		this.reusable = reusable;
	}
	
	@Override
	public boolean isReusable() {
		return reusable && !isClosed();
	}
	
	@Override
	public InputStream getInputStream() throws IOException {	  
//		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
//		String str = "";  
//        while((str = br.readLine()) != null) {  
//            str = str.trim();  
//            System.out.println("收到客户端消息：" + str);  
//        }  
		return new InputStreamWrapper(socket.getInputStream());
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return new OutputStreamWrapper(socket.getOutputStream());
	}
}
