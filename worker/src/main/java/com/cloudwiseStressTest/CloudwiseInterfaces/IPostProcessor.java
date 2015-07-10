package com.cloudwiseStressTest.CloudwiseInterfaces;

/** 
* @ClassName: IPostProcessor 
* @Description: 后置处理器接口
* @author jasonlee 
* @date 2015年7月2日 下午2:58:34 
*/
public interface IPostProcessor {
	
	/** 
	* @Description: 后置处理器的处理过程
	* @author jasonlee
	* @date 2015年7月2日
	*/
	void dealWithPostProcessor();
	
	/** 
	* @Description: 获取处理过程的错误信息
	* @author jasonlee
	* @date 2015年7月2日
	*/
	void getProcessorError();
}
