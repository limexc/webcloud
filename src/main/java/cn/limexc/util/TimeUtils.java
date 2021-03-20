package cn.limexc.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtils {
    private static SimpleDateFormat FORMATTIME = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    private volatile  static TimeUtils TIMEUTILS;
    private static String time;

    private TimeUtils() {
        System.out.println("创建了：timeutils实例");
    }

    public static TimeUtils getUtils() {
        Date date = new Date();
        if(null == TIMEUTILS) {
            synchronized(TimeUtils.class) {
                if(null == TIMEUTILS) {
                    TIMEUTILS = new TimeUtils();
                }
            }
        }
        time = FORMATTIME.format(date);


        return TIMEUTILS;
    }


    public String getForMatTime() {
        return time;
    }

}
