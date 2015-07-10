package com.google.code.http4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cloudwiseStressTest.CloudwiseClasses.CloudwiseThread;
import com.cloudwiseStressTest.CloudwiseClasses.CloudwiseThreadGroup;
import com.google.code.http4j.impl.BasicClient;
import com.google.code.http4j.impl.Get;
import com.google.code.http4j.utils.Metrics;
import com.google.code.http4j.utils.ThreadLocalMetricsRecorder;

public class test {
	 private static Map sysThreadMap=new HashMap();    
	 private static Map myThreadMap=new HashMap(); 
	public static void main(String[] args) {
		 File file = new File("src/main/java/com/cloudwiseStressTest/resource/config/demo.txt");
		 File file1 = new File("src/main/java/com/cloudwiseStressTest/resource/config/receiveData.txt");
		 FileReader reader;
			try {
				reader = new FileReader(file);
				 int fileLen = (int)file.length();
			     char[] chars = new char[fileLen];
				reader.read(chars);
				String txt = String.valueOf(chars); 				 
				JSONObject js = JSONObject.fromObject(txt);
				
				reader = new FileReader(file1);
				 int fileLen1 = (int)file1.length();
			     char[] chars1 = new char[fileLen1];
				reader.read(chars1);
				String txt1 = String.valueOf(chars1); 				 
				JSONArray jsa = JSONArray.fromObject(txt1);
				
				
		CloudwiseThreadGroup threadGroup = new CloudwiseThreadGroup(js, "firstTest", "firstCase", jsa);
			}catch(Exception e){
				
			}
		
//			System.out.println(jsa);
//			JSONObject timeJson = new JSONObject();
//			timeJson.put("startTime", "2015-07-09 17:14:00");
//			timeJson.put("endTime", "2015-07-09 17:14:10");
//			timeJson.put("startVU", 3);
//			timeJson.put("endVU", 6);
//			
//			JSONObject timeJson1 = new JSONObject();
//			timeJson1.put("startTime", "2015-07-09 17:14:10");
//			timeJson1.put("endTime", "2015-07-09 17:14:20");
//			timeJson1.put("startVU", 6);
//			timeJson1.put("endVU", 6);
//			
//			JSONObject timeJson2 = new JSONObject();
//			timeJson2.put("startTime", "2015-07-09 17:14:20");
//			timeJson2.put("endTime", "2015-07-09 17:14:30");
//			timeJson2.put("startVU", 8);
//			timeJson2.put("endVU", 0);
//			
//			JSONArray jsa = new JSONArray();
//			jsa.add(timeJson);
//			jsa.add(timeJson1);
//			jsa.add(timeJson2);
			
//		CloudwiseThread myThread = new CloudwiseThread();
//		for(int i=0;i<1;i++){
//			Thread test = new Thread(myThread);
//			String name = "thread"+i;
//			test.setName(name);
//			sysThreadMap.put(name, test);
//			myThreadMap.put(name, myThread);
//			test.start();
//			myThread.running = true;
//			myThread.groupRunning = true;
//		}
		
	
		
//		 Client client = new BasicClient();
//         Response response;
//		try {
//			
//			 File file = new File("/Users/lizy/Documents/demo.txt");
//		        FileReader reader = new FileReader(file);
//		        int fileLen = (int)file.length();
//		        char[] chars = new char[fileLen];
//		        reader.read(chars);
//		        String txt = String.valueOf(chars);
////		        System.out.println(txt);
//		        JSONObject js = JSONObject.fromObject(txt);
//		        System.out.println(js);
//		        JSONArray ja = js.getJSONArray("steps");
//		        JSONObject js1 = ja.getJSONObject(0);
//		        
//		        JSONObject header = js1.getJSONObject("header");
//			client.addHearderJson(header);
////			response = client.get("http://reg.163.com/");
//		      if(js1.getString("method").equals("get")){
//		    	  response = client.get(js1.getString("url"));
//		      }else{
//		    	  response = client.post(js1.getString("url"));
//		      }
//		      Metrics metrics = response.getMetrics();
//		      JSONObject reseltJs = new JSONObject();
//		      reseltJs.put("status", ThreadLocalMetricsRecorder.getInstance().getStatus());
//		      JSONObject requestInfor = new JSONObject();
//		      requestInfor.put("size", metrics.getBytesSent());
//		      requestInfor.put("content",ThreadLocalMetricsRecorder.getInstance().getReq());
//		      reseltJs.put("requestInfor",requestInfor );
//		      
//		      JSONObject responseContent = new JSONObject();
//		      responseContent.put("header", ThreadLocalMetricsRecorder.getInstance().getRes());
//		      responseContent.put("body",new String(response.getEntity()));
//		      
//		      JSONObject responseInfor = new JSONObject();
//		      responseInfor.put("size", metrics.getBytesReceived());
//		      responseInfor.put("content",responseContent);
//		      
//		     
//		      reseltJs.put("responseInfor", responseInfor);
//		      
//		      System.out.println("头部内容"+ThreadLocalMetricsRecorder.getInstance().getReq());
//		      
//			System.out.println("正文开始：");
//			System.out.println(new String(response.getEntity()));
//			
//			System.out.println("body长度"+new String(response.getEntity()).getBytes().length);
//			String fullFilename = "/Users/lizy/Documents/processedStr.txt";
//
//			try {
//			    File newTextFile = new File(fullFilename);
//			    FileWriter fw;
//			    fw = new FileWriter(newTextFile);
//			    fw.write(reseltJs.toString());
//			    fw.close();
//			} catch (IOException e) {
//			    // TODO Auto-generated catch block
//			    e.printStackTrace();
//			}
//			System.out.println("正文结束：");
//			
//	         System.out.println("Bytes sent:" + metrics.getBytesSent());
//	         System.out.println("Bytes received:" + metrics.getBytesReceived());
//	         System.out.println("Blocking cost:" + metrics.getBlockingCost());
//	         System.out.println("DNS lookup cost:" + metrics.getDnsLookupCost());
//	         
//	         System.out.println("DNS start time:" + metrics.getDnsStartTime()/1000000);
//	         System.out.println("DNS end time:" + metrics.getDnsEndTime()/1000000);
//	   
//	         System.out.println(metrics.getConnectingStartTime() - metrics.getDnsEndTime());
//	         
//	         System.out.println("Connection establish cost:" + metrics.getConnectingCost());
//	         
//	         System.out.println("Connection start time:" + metrics.getConnectingStartTime()/1000000);
//	         System.out.println("Connection end time:" + metrics.getConnectingEndTime()/1000000);
//	         
//	         System.out.println(metrics.getSendingStartTime()/1000000 - metrics.getConnectingEndTime()/1000000);
//	         
//	         System.out.println("Sending cost:" + metrics.getSendingCost()/1000000);
//	         
//	         System.out.println("Sending start time:" + metrics.getSendingStartTime()/1000000);
//	         System.out.println("Sending end time:" + metrics.getSendingEndTime()/1000000);
//	         
//	         System.out.println("Waiting cost:" + metrics.getWaitingCost());
//	         
//	         System.out.println("Waiting start time:" + metrics.getWaitingStartTime()/1000000);
//	         System.out.println("Waiting end time:" + metrics.getWaitingEndTime()/1000000);
//	         
//	         System.out.println("Receiving cost:" + metrics.getReceivingCost());
//	         
//	         System.out.println("Receiving start time:" + metrics.getReceivingStartTime()/1000000);
//	         System.out.println("Receiving end time:" + metrics.getReceivingEndTime()/1000000);
//	         
//	         System.out.println("SSL handshake cost:" + metrics.getSslHandshakeCost());
////	         response.output(System.out);
//	         client.shutdown();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
