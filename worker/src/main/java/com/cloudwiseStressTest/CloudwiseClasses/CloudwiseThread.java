package com.cloudwiseStressTest.CloudwiseClasses;

import java.io.File;
import java.io.FileReader;

import net.sf.json.JSONObject;

import com.cloudwiseStressTest.CloudwiseInterfaces.ICloudwiseTest;
import com.cloudwiseStressTest.CloudwiseInterfaces.ICloudwiseThread;


public class CloudwiseThread  implements Runnable,ICloudwiseThread{
	volatile int threadNum;
	volatile int returnNum;
	public boolean running = false;
	private JSONObject model;
	
	public JSONObject getModel() {
		return model;
	}

	public void setModel(JSONObject model) {
		this.model = model;
	}

	public CloudwiseThread(JSONObject model){
		this.model = model;
	}
	
	@Override
	public void run(){
		threadNum++;
		while(running){
			ICloudwiseTest test = new CloudwiseHttpTest(model);
			test.run();
			returnNum++;
//			System.out.println("返回结果次数"+returnNum);
		}
		
	}
	
	
	
	@Override
	public void stop(){
		running = false;
//		threadNum--;
//		System.out.println("当前VU数"+threadNum);
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void caseInstantiation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reRun() {
		running = true;
		run();
	}
	
}
