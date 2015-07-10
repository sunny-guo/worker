package com.cloudwiseStressTest.CloudwiseInterfaces;

import java.sql.Date;


/** 
* @ClassName: ICloudwiseThreadGroup 
* @Description: 该接口用于定义测试VU管理的方法
* @author jasonlee 
* @date 2015年7月9日 上午11:26:47 
*/
public interface ICloudwiseThreadGroup {
	
	/** 
	* @Description: thread增长控制
	* @param threadCount 增长数量
	* @param sleepTime 增长间隔时间
	* @author jasonlee
	* @date 2015年7月9日
	*/
	void riseThreadNum(Long startTimeSleep, Long threadCount,Long sleepTime);
	
	/** 
	* @Description: thread减少控制
	* @param threadCount 减少数量
	* @param sleepTime 减少间隔时间
	* @author jasonlee
	* @date 2015年7月9日
	*/
	void dropThreadNum(Long startTimeSleep,Long threadCount,Long sleepTime);
	
	/** 
	* @Description: 获取当前运行的线程数
	* @author jasonlee
	* @date 2015年7月9日
	*/
	int getCurrentThreadCount();
	
	/** 
	* @Description:线程组重新启动
	* @author jasonlee
	* @date 2015年7月9日
	*/
	void reRun();
	
	/** 
	* @Description:线程组暂停
	* @author jasonlee
	* @date 2015年7月9日
	*/
	void pause();
	
	/** 
	* @Description: 线程组停止
	* @author jasonlee
	* @date 2015年7月9日
	*/
	void stop();
	
}
