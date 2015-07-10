package com.google.code.http4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.code.http4j.impl.BasicClient;
import com.google.code.http4j.utils.Metrics;
import com.google.code.http4j.utils.ThreadLocalMetricsRecorder;

public class TestThread implements Runnable {
	volatile int threadNum;
	volatile int returnNum;
	volatile Long totalNum;
	volatile Long transNum;
	
	public volatile boolean groupRunning;
	public boolean running = false;
	@Override
	public void run() {
		threadNum++;
		System.out.println("当前VU数"+threadNum);
		// TODO Auto-generated method stub
		 Client client = new BasicClient();
         Response response;
		try {
			
			 File file = new File("/Users/lizy/Documents/demo.txt");
		        FileReader reader = new FileReader(file);
		        int fileLen = (int)file.length();
		        char[] chars = new char[fileLen];
		        reader.read(chars);
		        String txt = String.valueOf(chars);
//		        System.out.println(txt);
		        JSONObject js = JSONObject.fromObject(txt);
		        System.out.println(js);
		        JSONArray ja = js.getJSONArray("steps");
		        JSONObject js1 = ja.getJSONObject(0);
		        
		        JSONObject header = js1.getJSONObject("header");
			client.addHearderJson(header);
//			response = client.get("http://reg.163.com/");
		      if(js1.getString("method").equals("get")){
		    	  response = client.get(js1.getString("url"));
		      }else{
		    	  response = client.post(js1.getString("url"));
		      }
		      Metrics metrics = response.getMetrics();
		      JSONObject reseltJs = new JSONObject();
		      reseltJs.put("status", ThreadLocalMetricsRecorder.getInstance().getStatus());
		      JSONObject requestInfor = new JSONObject();
		      requestInfor.put("size", metrics.getBytesSent());
		      requestInfor.put("content",ThreadLocalMetricsRecorder.getInstance().getReq());
		      reseltJs.put("requestInfor",requestInfor );
		      
		      JSONObject responseContent = new JSONObject();
		      responseContent.put("header", ThreadLocalMetricsRecorder.getInstance().getRes());
		      responseContent.put("body",new String(response.getEntity()));
		      
		      JSONObject responseInfor = new JSONObject();
		      responseInfor.put("size", metrics.getBytesReceived());
		      responseInfor.put("content",responseContent);
		      
		     
		      reseltJs.put("responseInfor", responseInfor);
		      
//		      System.out.println("头部内容"+ThreadLocalMetricsRecorder.getInstance().getReq());
//		      
//			System.out.println("正文开始：");
//			System.out.println(new String(response.getEntity()));
//			
//			System.out.println("body长度"+new String(response.getEntity()).getBytes().length);
			String fullFilename = "/Users/lizy/Documents/processedStr.txt";

			try {
			    File newTextFile = new File(fullFilename);
			    FileWriter fw;
			    fw = new FileWriter(newTextFile);
			    fw.write(reseltJs.toString());
			    fw.close();
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
//			System.out.println("正文结束：");
			
	         System.out.println("Bytes sent:" + metrics.getBytesSent());
	         System.out.println("Bytes received:" + metrics.getBytesReceived());
	         System.out.println("Blocking cost:" + metrics.getBlockingCost());
	         System.out.println("DNS lookup cost:" + metrics.getDnsLookupCost()/1000000);
	         
//	         System.out.println("DNS start time:" + metrics.getDnsStartTime()/1000000);
//	         System.out.println("DNS end time:" + metrics.getDnsEndTime()/1000000);
	   
	         System.out.println(metrics.getConnectingStartTime() - metrics.getDnsEndTime());
	         
	         System.out.println("Connection establish cost:" + metrics.getConnectingCost()/1000000);
	         
//	         System.out.println("Connection start time:" + metrics.getConnectingStartTime()/1000000);
//	         System.out.println("Connection end time:" + metrics.getConnectingEndTime()/1000000);
	         
	         System.out.println(metrics.getSendingStartTime() - metrics.getConnectingEndTime());
	         
	         System.out.println("Sending cost:" + metrics.getSendingCost()/1000000);
	         
//	         System.out.println("Sending start time:" + metrics.getSendingStartTime()/1000000);
//	         System.out.println("Sending end time:" + metrics.getSendingEndTime()/1000000);
	         
	         System.out.println("Waiting cost:" + metrics.getWaitingCost()/1000000);
	         
//	         System.out.println("Waiting start time:" + metrics.getWaitingStartTime()/1000000);
//	         System.out.println("Waiting end time:" + metrics.getWaitingEndTime()/1000000);
	         
	         System.out.println("Receiving cost:" + metrics.getReceivingCost()/1000000);
//	        if(totalNum == null){
//	        	totalNum = 0L;
//	        }
//	        if(transNum == null){
//	        	transNum = 0L;
//	        }
//			totalNum = totalNum+metrics.getDnsLookupCost()/1000000;
//			transNum = transNum+metrics.getReceivingCost()/1000000;
//	         System.out.println("Receiving start time:" + metrics.getReceivingStartTime()/1000000);
//	         System.out.println("Receiving end time:" + metrics.getReceivingEndTime()/1000000);
	         
//	         System.out.println("SSL handshake cost:" + metrics.getSslHandshakeCost());
//	         response.output(System.out);
//	         returnNum++;
//				System.out.println("返回结果次数"+returnNum+"总时间"+totalNum+"DNS平均时间"+totalNum/returnNum);
//				System.out.println("返回结果次数"+returnNum+"总时间"+transNum+"Trans平均时间"+transNum/returnNum);
				
	         client.shutdown();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
