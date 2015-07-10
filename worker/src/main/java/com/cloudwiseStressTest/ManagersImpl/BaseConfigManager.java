package com.cloudwiseStressTest.ManagersImpl;

import java.io.File;
import java.io.FileReader;

import net.sf.json.JSONObject;

/** 
* @ClassName: BaseConfigManager 
* @Description: 该类用于解析配置文件
* @author jasonlee 
* @date 2015年6月26日 下午4:23:57 
*/
public class BaseConfigManager {
	private static JSONObject baseHeader;
	private static String resultUrl;
	private static int maxLooptimes;
	
	/**解析配置文件 转储对象
	 * @date 2015/6/26
	 * @author jasonlee
	 */
	static {
		File file = new File("src/main/java/com/cloudwiseStressTest/resource/config/workerConfig.property");
        FileReader reader;
		try {
			reader = new FileReader(file);
			 int fileLen = (int)file.length();
		     char[] chars = new char[fileLen];
			reader.read(chars);
			 String txt = String.valueOf(chars);
			 JSONObject baseJson = JSONObject.fromObject(txt);
			 baseHeader = baseJson.getJSONObject("baseHeader");
			 resultUrl = baseJson.getString("resultUrl");
			 maxLooptimes = baseJson.getInt("max-looptimes");
		}catch(Exception e){
			
		}
	}
	
	public static JSONObject getBaseHeader() {
		return baseHeader;
	}
	public static void setBaseHeader(JSONObject baseHeader) {
		BaseConfigManager.baseHeader = baseHeader;
	}
	public static String getResultUrl() {
		return resultUrl;
	}
	public static void setResultUrl(String resultUrl) {
		BaseConfigManager.resultUrl = resultUrl;
	}
	public static int getMaxLooptimes() {
		return maxLooptimes;
	}
	public static void setMaxLooptimes(int maxLooptimes) {
		BaseConfigManager.maxLooptimes = maxLooptimes;
	}
}
