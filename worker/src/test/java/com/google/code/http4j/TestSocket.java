package com.google.code.http4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestSocket {
public static void main(String args[]){
try
{
//Post请求格式如下:
String postStr = "userName=rzb001&passWord=rzb001";
int postStrLen = postStr.length();
StringBuilder post = new StringBuilder("POST /login/check.do HTTP/1.1\r\n");
post.append("Host: localhost:8080\r\n");
post.append("Accept: text/html\r\n");
post.append("Connection: Close\r\n");
post.append("Content-Length: "+postStrLen+"\r\n");
post.append("Content-Type: application/x-www-form-urlencoded\r\n");//*
post.append("\r\n");
post.append(postStr);
System.out.println(post);//post
// HTTP POST其他请求头如下
// post.append("User-Agent: Java/1.6.0_20rn");
// post.append("Accept-Charset: gb2312,utf-8;q=0.7,*;q=0.7");
Socket socket=new Socket("localhost",8080);
//PrintWriter os=new PrintWriter(socket.getOutputStream());//用于发送
BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));//用于接收
//os.println(post);//发送post请求
//os.flush();
socket.getOutputStream().write(post.toString().getBytes());
socket.getOutputStream().flush();
System.out.println("Server:");
       String tmp = "";  
       while((tmp = is.readLine())!=null){  
           System.out.println(tmp);  
       }
//       os.close();  
       is.close(); 
System.out.println("end");
}catch(Exception e){
e.printStackTrace();
}
}
}
