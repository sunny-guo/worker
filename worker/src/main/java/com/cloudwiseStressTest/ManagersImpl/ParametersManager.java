package com.cloudwiseStressTest.ManagersImpl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;

/**该类用于管理测试需要的公共参数
 * @date 2015/6/26
 * @author jasonlee
 */
public class ParametersManager implements com.cloudwiseStressTest.CloudwiseInterfaces.IParametersManager{
	public static Map<String,String> paramsMap;

	@Override
	public Map<String, String> getParamsMap() {
		return paramsMap;
	}

	@Override
	public void setParamsMap(Map<String, String> paramsMap) {
		ParametersManager.paramsMap = paramsMap;
	}
	
	@Override
	public void setupParamsMap(JSONArray params){
		paramsMap =  new HashMap<String,String>();
		for(int i = 0;i<params.size();i++){
			paramsMap.put(params.getJSONObject(i).getString("name"),
					params.getJSONObject(i).get("value") != null?params.getJSONObject(i)
							.getString("value"):"");
		}
	}

	@Override
	public String getValueBykey(String key) {
		if(paramsMap.get(key)!=null){
			return paramsMap.get(key).toString();
		}
		return null;
	}
	
	
	
}
