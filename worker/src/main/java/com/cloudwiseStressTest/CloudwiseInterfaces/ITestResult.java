package com.cloudwiseStressTest.CloudwiseInterfaces;

import net.sf.json.JSONObject;

/** 
* @ClassName: ITestResult 
* @Description: 该接口用于测试结果的组装
* @author jasonlee 
* @date 2015年7月1日 下午4:55:38 
*/
public interface ITestResult {
	
	/** 
	* @Description: 设置测试名称
	* @author jasonlee
	* @date 2015年7月1日
	*/
	void setTestName(String name);
	
	/** 
	* @Description: 设置结果子项
	* @author jasonlee
	* @date 2015年7月1日
	*/
	void putSubResult(String desc,JSONObject subResult);
	
	/** 
	* @Description: 获取测试结果 
	* @author jasonlee
	* @date 2015年7月1日
	*/
	JSONObject getHttpTestResult();
	
	
}
