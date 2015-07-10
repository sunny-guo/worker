package com.cloudwiseStressTest.CloudwiseClasses;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cloudwiseStressTest.CloudwiseInterfaces.ICloudwiseThreadGroup;
import com.google.code.http4j.utils.DateUtil;
import com.google.code.http4j.utils.TimerUtil;

public class CloudwiseThreadGroup implements ICloudwiseThreadGroup{

	
	/** 
	* @Fields caseModel : 测试场景模版
	*/
	private JSONObject caseModel;
	
	/** 
	* @Fields testName : 测试名称
	*/
	private String testName;

	/** 
	* @Fields caseName : 场景名称
	*/
	private String caseName;

	/** 
	* @Fields timePoriodForVU : 时间VU片段
	*/
	private JSONArray timePoriodForVU;

	/** 
	* @Fields groupRunning : 测试场景启动或停止
	*/
	private Boolean	groupRunning;
	
	/** 
	* @Fields currentThreadNum :当前运行的线程数
	*/
	private int currentThreadCount;

	/** 
	* @Fields sysThreadMap : 管理所有运行中Thread的集合
	*/
	private static ArrayList<Thread> sysThreadMap=new ArrayList<Thread>();    
	 /** 
	* @Fields myThreadMap : 管理所有运行中CloudwiseThread的集合
	*/
	private static ArrayList<CloudwiseThread> myThreadMap=new ArrayList<CloudwiseThread>(); 
	
	public CloudwiseThreadGroup(JSONObject caseModel,String testName,String caseName,JSONArray timePoriodForVU){
		this.caseModel = caseModel;
		this.testName = testName;
		this.caseName = caseName;
		this.timePoriodForVU = timePoriodForVU;
		dealTimePoriodForVU();
	}
	
	/** 
	* @Description:  时间VU曲线转化
	* @author jasonlee
	* @date 2015年7月9日
	*/
	private void dealTimePoriodForVU(){
		Long startTime;
		Long endTime;
		Long startVU;
		Long endVU;
		
		for(int i=0;i<timePoriodForVU.size();i++){
			startTime = DateUtil.toTime(timePoriodForVU.getJSONObject(i).getString("startTime")).getTime();
			endTime =  DateUtil.toTime(timePoriodForVU.getJSONObject(i).getString("endTime")).getTime();;
			startVU = timePoriodForVU.getJSONObject(i).getLong("startVU");
			endVU = timePoriodForVU.getJSONObject(i).getLong("endVU");
			
			if(i == 0){//第一个时间片段特殊处理
				riseThreadNum(startTime-System.currentTimeMillis(),startVU,0L);
				dealWithSinglePariod(startTime,endTime,startVU,endVU);
			}else{
				if(startVU != timePoriodForVU.getJSONObject(i-1).getLong("endVU")){//判断起始VU与上一片段的终止VU是否相等
					dealWithSinglePariod(startTime,startTime,timePoriodForVU.getJSONObject(i-1).getLong("endVU"),startVU);
				}
				dealWithSinglePariod(startTime,endTime,startVU,endVU);
			}
		}
	}
	
	/** 
	* @Description:  单一时间片段的处理
	* @author jasonlee
	* @date 2015年7月9日
	*/
	public void dealWithSinglePariod(Long startTime,Long endTime,Long startVU,Long endVU){
		if(endVU>startVU){//上升线
			riseThreadNum(startTime-System.currentTimeMillis(),endVU - startVU,(endTime-startTime)/(endVU - startVU));
		}else if(endVU<startVU){//下降线
			dropThreadNum(startTime-System.currentTimeMillis(),startVU - endVU,(endTime-startTime)/(startVU - endVU));
		}
	}
	
	@Override
	public void riseThreadNum(Long startTimeSleep,Long threadCount, Long sleepTime) {
		startTimeSleep = startTimeSleep<=0?0:startTimeSleep;
		TimerUtil.pause(startTimeSleep);
		CloudwiseThread myThread = new CloudwiseThread(caseModel);
		for(int i=0;i<threadCount;i++){
			TimerUtil.pause(sleepTime);
			Thread singleThread = new Thread(myThread);
			sysThreadMap.add(singleThread);
			myThreadMap.add(myThread);
			singleThread.start();
			myThread.running = true;
			setCurrentThreadCount(sysThreadMap.size());
			System.out.println("当前运行的时间："+DateUtil.getCurrDateTime()+";;;当前运行的VU数："+sysThreadMap.size());
		}
		
	}

	@Override
	public void dropThreadNum(Long startTimeSleep,Long threadCount, Long sleepTime) {
		startTimeSleep = startTimeSleep<=0?0:startTimeSleep;
		TimerUtil.pause(startTimeSleep);
		for(int i=0;i<threadCount;i++){
			TimerUtil.pause(sleepTime);
			myThreadMap.get(0).stop();
			myThreadMap.remove(0);
			sysThreadMap.remove(0);	
			System.out.println("当前运行的时间："+DateUtil.getCurrDateTime()+";;;当前运行的VU数："+sysThreadMap.size());
		}
	}

	@Override
	public int getCurrentThreadCount() {
		return currentThreadCount;
	}

	@Override
	public void reRun() {
		for(int i=0;i<myThreadMap.size();i++){
			myThreadMap.get(i).reRun();	
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void stop() {
		for(int i=0;i<myThreadMap.size();i++){
			myThreadMap.get(0).stop();
			myThreadMap.remove(0);
			sysThreadMap.remove(0);	
		}	
	}
	
	public void setCurrentThreadCount(int currentThreadCount) {
		this.currentThreadCount = currentThreadCount;
	}
	
	
	public JSONObject getCaseModel() {
		return caseModel;
	}

	public void setCaseModel(JSONObject caseModel) {
		this.caseModel = caseModel;
	}

	public Boolean getGroupRunning() {
		return groupRunning;
	}

	public void setGroupRunning(Boolean groupRunning) {
		this.groupRunning = groupRunning;
	}
	
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
	public JSONArray getTimePoriodForVU() {
		return timePoriodForVU;
	}

	public void setTimePoriodForVU(JSONArray timePoriodForVU) {
		this.timePoriodForVU = timePoriodForVU;
	}
}
