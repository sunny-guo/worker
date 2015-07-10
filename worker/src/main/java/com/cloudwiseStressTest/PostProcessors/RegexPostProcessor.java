package com.cloudwiseStressTest.PostProcessors;

import java.awt.List;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cloudwiseStressTest.CloudwiseInterfaces.IPostProcessor;
import com.cloudwiseStressTest.ManagersImpl.ParametersManager;
import com.cloudwiseStressTest.ResultManager.SingleHttpStepResult;

public class RegexPostProcessor implements IPostProcessor {
	/** 
	* @Fields field : 匹配域
	*/
	private String field;
	/** 
	* @Fields storeName : 取值后的存储变量 
	*/
	private String storeName;
	/** 
	* @Fields regex : 用于取值的正则表达式
	*/
	private String regex;
	
	/** 
	* @Fields num : 匹配个数 
	*/
	private int regexNum;

	private SingleHttpStepResult result;
	
	public RegexPostProcessor(){
		
	}
	
	public RegexPostProcessor(String storeName,String regex,
			SingleHttpStepResult result,int regexNum){
		this.regex = regex;
		this.storeName = storeName;
		this.result = result;
		this.regexNum = regexNum;
	}
	
	@Override
	public void dealWithPostProcessor() { 
	     Pattern pattern = Pattern.compile(".+?>(.+?),你好.+?");  
	     Matcher matcher = pattern.matcher(result.getBody()); 
	     ArrayList<String> matchs = new ArrayList<String>();
	     int i = 0;
	     while(matcher.find()){
	    	 if(regexNum==-1){
	    		 matchs.add(matcher.group(1));
	    	 }else if(i<regexNum){
	    		 matchs.add(matcher.group(1));
	    		 i++;
	    	 }else{
	    		 break;
	    	 } 
	     }
	     ParametersManager.paramsMap.put(storeName, matchs.toString());
//	     System.out.println("正则表达式处理存储值："+ParametersManager.paramsMap.get(storeName));
	}

	@Override
	public void getProcessorError() {
		// TODO Auto-generated method stub

	}
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public int getRegexNum() {
		return regexNum;
	}

	public void setRegexNum(int regexNum) {
		this.regexNum = regexNum;
	}
	
}
