package com.cloudwiseStressTest.CloudwiseClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class CloudwiseFileReader extends InputStreamReader{
	 public CloudwiseFileReader(String file,String charSetName) throws FileNotFoundException, UnsupportedEncodingException {
	        super(new FileInputStream(file),charSetName);
	    }
}
