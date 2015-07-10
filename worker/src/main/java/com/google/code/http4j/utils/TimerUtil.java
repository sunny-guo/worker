package com.google.code.http4j.utils;

import java.util.concurrent.TimeUnit;

/** 
* @ClassName: TimerUtil 
* @Description: 处理sleep等时间问题
* @author jasonlee 
* @date 2015年7月9日 下午1:50:56 
*/
public class TimerUtil {
    public static void pause(long ms){
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            // TODO Is this silent exception intended
        }
    }
}
