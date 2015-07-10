package com.cloudwiseStressTest.CloudwiseInterfaces;

import java.util.Map;

import net.sf.json.JSONArray;

/** 
* @ClassName: ParametersManager 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author jasonlee 
* @date 2015年6月30日 上午9:41:49 
*/
public interface IParametersManager {

	/** 
	* @Description: 解析params并存储
	* @author jasonlee
	* @date 2015年6月30日
	*/
	void setupParamsMap(JSONArray params);
	
	/** 
	* @Description: 获取公共参数集合
	* @author jasonlee
	* @date 2015年6月30日
	*/
	Map<String, String> getParamsMap();
	
	/** 
	* @Description: 初始化公共参数集合
	* @author jasonlee
	* @date 2015年6月30日
	*/
	void setParamsMap(Map<String, String> paramsMap);
	
	/** 
	* @Description: 根据key获取公共参数变量值
	* @author jasonlee
	* @date 2015年7月3日
	*/
	String getValueBykey(String key);
}
