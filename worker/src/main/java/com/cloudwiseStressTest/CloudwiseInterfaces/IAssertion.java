package com.cloudwiseStressTest.CloudwiseInterfaces;

import net.sf.json.JSONObject;

/** 
* @ClassName: IAssertion 
* @Description: 该接口用于断言处理
* @author jasonlee 
* @date 2015年7月1日 下午6:06:49 
*/
public interface IAssertion {
	/** 
	* @Description: 处理断言
	* @author jasonlee
	* @date 2015年7月1日
	*/
	void dealWithAssertion();
	
	/** 
	* @Description: 获取断言结果
	* @author jasonlee
	* @date 2015年7月1日
	*/
	JSONObject getAssertionResult();
}
