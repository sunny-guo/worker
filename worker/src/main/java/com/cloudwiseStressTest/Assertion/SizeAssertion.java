package com.cloudwiseStressTest.Assertion;

import net.sf.json.JSONObject;

import com.cloudwiseStressTest.CloudwiseInterfaces.IAssertion;

public class SizeAssertion implements IAssertion {
	/** 
	* @Fields size :文本大小
	*/
	private int size;
	
	/** 
	* @Fields mode : 匹配模式：＝；!= ;  > ;  < ; >= ; <=
	*/
	private String mode;
	
	/** 
	* @Fields value : 匹配值
	*/
	private int value;
	
	/** 
	* @Fields result : 断言结果
	*/
	private JSONObject result = new JSONObject();
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public SizeAssertion(int size,String mode,int value){
		this.size = size;
		this.mode = mode;
		this.value = value;
	}

	public void dealWithAssertion() {
		String resultString = "";
		switch(mode){
		case "=":
			resultString =(size == value?"通过":"不通过");break;
		case "!=":
			resultString =(size != value?"通过":"不通过");break;
		case ">":
			resultString =(size > value?"通过":"不通过");break;
		case "<":
			resultString =(size < value?"通过":"不通过");break;
		case ">=":
			resultString =(size >= value?"通过":"不通过");break;
		case "<=":
			resultString =(size <= value?"通过":"不通过");break;
			default:break;
		}
		result.put("expression", size+mode+value);
		result.put("sizeAssertionResult", resultString);
	}

	@Override
	public JSONObject getAssertionResult() {
		return result;
	}

}
