package com.cloudwiseStressTest.ResultManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cloudwiseStressTest.CloudwiseInterfaces.ITestResult;

public class HttpTestResult implements ITestResult {
	private JSONObject httpTestResult;
	private JSONArray subResults;
	
	public HttpTestResult(){
		 httpTestResult = new JSONObject();
		 subResults = new JSONArray();
	}
	
	public JSONObject getHttpTestResult() {
		return httpTestResult;
	}

	public void setTestName(String name) {
		httpTestResult.put("testName", name);
	}

	public void putSubResult(String desc, JSONObject subResult) {
		JSONObject subResultMade = new JSONObject();
		subResultMade.put(desc, subResult);
		subResults.add(subResultMade);
		httpTestResult.put("stepResults",subResults);
	}

}
