package com.cloudwiseStressTest.CloudwiseInterfaces;

import net.sf.json.JSONObject;

/** 
* @ClassName: ISingleStepResult 
* @Description: 单步测试结果处理接口
* @author jasonlee 
* @date 2015年7月1日 下午6:45:46 
*/
public interface ISingleStepResult {

	/** 
	* @Description: 组装单步测试的待发送结果集
	* @author jasonlee
	* @date 2015年7月1日
	*/
	JSONObject generateResultToTransport();
	
	/** 
	* @Description: 获取组装完成的单步结果
	* @author jasonlee
	* @date 2015年7月1日
	*/
	JSONObject getResultToTransport();
}
