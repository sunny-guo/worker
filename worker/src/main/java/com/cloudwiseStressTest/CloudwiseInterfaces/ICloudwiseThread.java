package com.cloudwiseStressTest.CloudwiseInterfaces;

/** 
* @ClassName: ICloudwiseThread 
* @Description: 线程－VU 管理
* @author jasonlee 
* @date 2015年7月9日 上午11:34:20 
*/
public interface ICloudwiseThread {
	
	/** 
	* @Description: 线程启动 
	* @author jasonlee
	* @date 2015年7月9日
	*/
	void run();
	
	/** 
	* @Description: 线程停止
	* @author jasonlee
	* @date 2015年7月9日
	*/
	void stop();
	
	/** 
	* @Description: 线程暂停
	* @author jasonlee
	* @date 2015年7月9日
	*/
	void pause();
	
	/** 
	* @Description: 线程重启
	* @author jasonlee
	* @date 2015年7月9日
	*/
	void reRun();
	
	/** 
	* @Description: 模版实例化
	* @author jasonlee
	* @date 2015年7月9日
	*/
	void caseInstantiation();
}
