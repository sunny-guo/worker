package com.cloudwiseStressTest.CloudwiseInterfaces;

import com.cloudwiseStressTest.ResultManager.SingleHttpStepResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
* @ClassName: CloudwiseTest 
* @Description: 该接口为各类型测试的接口类
* @author jasonlee 
* @date 2015年6月29日 下午4:19:59 
*/
public interface ICloudwiseTest {
	
	/** 
	* @Description: 获取测试名称 
	* @author jasonlee
	* @date 2015年6月29日
	*/
	String getTestName();
	
	/** 
	* @Description: 启动测试 
	* @author jasonlee
	* @date 2015年6月29日
	*/
	JSONObject run();
	
	/** 
	* @Description: 处理后置
	* @param singleResult  请求结果对象
	* @author jasonlee
	* @date 2015年7月1日
	*/
	void dealWithPostProcessors(SingleHttpStepResult singleResult,JSONArray postProcessors);
	
	/** 
	* @Description: 暂停测试 
	* @author jasonlee
	* @date 2015年6月29日
	*/
	void pause();
	
	/** 
	* @Description: 终止测试 
	* @author jasonlee
	* @date 2015年6月29日
	*/
	void stop();
	
	/** 
	* @Description: 处理断言
	* @author jasonlee
	* @date 2015年7月1日
	*/
	void dealWithAssertions(SingleHttpStepResult single,JSONArray assertions);
	
}
