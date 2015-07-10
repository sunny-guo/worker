package com.cloudwiseStressTest.ResultManager;

import com.cloudwiseStressTest.CloudwiseInterfaces.ISingleStepResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
* @ClassName: SingleHttpStepResult 
* @Description: 单步http请求的结果处理
* @author jasonlee 
* @date 2015年7月2日 上午9:46:30 
*/
public class SingleHttpStepResult implements ISingleStepResult{
	private JSONObject requestHeader;
	private JSONObject responseHeader;
	private String body;
	private String status;
	private Long absoluteEndTime;
	private JSONObject time;
	private JSONObject size; 
	private JSONObject resultToTransport;
	private JSONArray assertions = new JSONArray();
	


	/** 
	* @Description: 组装单一http请求的待发送结果集
	* @return JSONObject
	* @author jasonlee
	* @date 2015年6月30日
	*/
	public JSONObject generateResultToTransport(){
		resultToTransport = new JSONObject();
		resultToTransport.put("requestHeader", requestHeader);
		resultToTransport.put("responseHeader", responseHeader);
		resultToTransport.put("body", body);
		resultToTransport.put("status", status);
		resultToTransport.put("time", time);
		resultToTransport.put("size", size);
		resultToTransport.put("endTime", absoluteEndTime);
		if(assertions.size()>0){
			resultToTransport.put("assertions", assertions);	
		}
		return resultToTransport;
	}
	
	/* (non-Javadoc)
	 * @see com.jasonlee.cloudwiseStressTest.CloudwiseInterfaces.ISingleStepResult#getResultToTransport()
	 */
	public JSONObject getResultToTransport() {
		return resultToTransport;
	}
	public void setResultToTransport(JSONObject resultToTransport) {
		this.resultToTransport = resultToTransport;
	}
	public JSONObject getSize() {
		return size;
	}
	public void setSize(JSONObject size) {
		this.size = size;
	}
	public JSONObject getTime() {
		return time;
	}
	public void setTime(JSONObject time) {
		this.time = time;
	}

	public JSONArray getAssertions() {
		return assertions;
	}

	public void setAssertions(JSONArray assertions) {
		this.assertions = assertions;
	}
	
	public Long getAbsoluteEndTime() {
		return absoluteEndTime;
	}

	public void setAbsoluteEndTime(Long absoluteEndTime) {
		this.absoluteEndTime = absoluteEndTime;
	}
	
	public JSONObject getRequestHeader() {
		return requestHeader;
	}
	public void setRequestHeader(JSONObject requestHeader) {
		this.requestHeader = requestHeader;
	}
	
	public JSONObject getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(JSONObject responseHeader) {
		this.responseHeader = responseHeader;
	}
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
