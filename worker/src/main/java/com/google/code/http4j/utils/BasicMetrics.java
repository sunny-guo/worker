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

package com.google.code.http4j.utils;

/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 * 
 */
public class BasicMetrics implements Metrics {

	protected final long blockingCost;
	
	protected final long dnsLookupCost;
	
	protected final long dnsStartTime;

	protected final long dnsEndTime;

	protected final long connectingCost;
	
	protected final long connectingStartTime;
	
	protected final long connectingEndTime;

	protected final long sendingCost;
	
	protected final long sendingStartTime;
	
	protected final long sendingEndTime;

	protected final long waitingCost;
	
	protected final long waitingStartTime;
	
	protected final long waitingEndTime;

	protected final long receivingCost;

	protected final long receivingStartTime;
	
	protected final long receivingEndTime;
	
	protected final long bytesSent;

	protected final long bytesReceived;

	protected final long sslHandshakeCost;

	protected Metrics parentMetrics;
	
	public BasicMetrics(long blockingCost, long dnsLookupCost, long connectingCost,
			long sendingCost, long waitingCost, long receivingCost,
			long sslHandshakeCost, long bytesSent, long bytesReceived,long dnsStartTime,
			long dnsEndTime,long connectingStartTime,long connectingEndTime,long sendingStartTime,
			long sendingEndTime,long waitingStartTime,long waitingEndTime,long receivingStartTime,
			long receivingEndTime) {
		this.blockingCost = blockingCost;
		this.dnsLookupCost = dnsLookupCost;
		this.connectingCost = connectingCost;
		this.sendingCost = sendingCost;
		this.waitingCost = waitingCost;
		this.receivingCost = receivingCost;
		this.sslHandshakeCost = sslHandshakeCost;
		this.bytesSent = bytesSent;
		this.bytesReceived = bytesReceived;
		this.dnsStartTime = dnsStartTime;
		this.dnsEndTime = dnsEndTime;
		this.connectingStartTime = connectingStartTime;
		this.connectingEndTime = connectingEndTime;
		this.sendingStartTime = sendingStartTime;
		this.sendingEndTime = sendingEndTime;
		this.waitingStartTime = waitingStartTime;
		this.waitingEndTime = waitingEndTime;
		this.receivingStartTime = receivingStartTime;
		this.receivingEndTime = receivingEndTime;
	}

	public long getBlockingCost() {
		return blockingCost;
	}

	public long getDnsLookupCost() {
		return dnsLookupCost;
	}

	public long getConnectingCost() {
		return connectingCost;
	}

	public long getSendingCost() {
		return sendingCost;
	}

	public long getWaitingCost() {
		return waitingCost;
	}

	public long getReceivingCost() {
		return receivingCost;
	}

	public long getBytesSent() {
		return bytesSent;
	}

	public long getBytesReceived() {
		return bytesReceived;
	}

	public long getSslHandshakeCost() {
		return sslHandshakeCost;
	}

	@Override
	public Metrics getParentMetrics() {
		return parentMetrics;
	}

	@Override
	public void setParentMetrics(Metrics sourceMetrics) {
		this.parentMetrics = sourceMetrics;
	}

	public long getDnsStartTime() {
		return dnsStartTime;
	}

	public long getDnsEndTime() {
		return dnsEndTime;
	}

	public long getConnectingStartTime() {
		return connectingStartTime;
	}

	public long getConnectingEndTime() {
		return connectingEndTime;
	}

	public long getSendingStartTime() {
		return sendingStartTime;
	}

	public long getSendingEndTime() {
		return sendingEndTime;
	}

	public long getWaitingStartTime() {
		return waitingStartTime;
	}

	public long getWaitingEndTime() {
		return waitingEndTime;
	}

	public long getReceivingStartTime() {
		return receivingStartTime;
	}

	public long getReceivingEndTime() {
		return receivingEndTime;
	}
	
	@Override
	public String toString() {
		return "blockingCost=" + blockingCost
				+ ", bytesReceived=" + bytesReceived + ", bytesSent="
				+ bytesSent + ", connectingCost=" + connectingCost
				+ ", dnsLookupCost=" + dnsLookupCost + ", parentMetrics="
				+ parentMetrics + ", receivingCost=" + receivingCost
				+ ", sendingCost=" + sendingCost + ", sslHandshakeCost="
				+ sslHandshakeCost + ", waitingCost=" + waitingCost;
	}

}
