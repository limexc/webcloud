package cn.limexc.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtils {
    public volatile static TimeUtils timeUtils;

    private TimeUtils(){

    }
    
    private static SimpleDateFormat FORMATTIME = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    /**
     * 不知道怎么想的了，先这样吧，后期改单例模式会好一些   大概？
     * @return  返回格式化后的时间
     */
    public String getForMatTime(){

        if (null == timeUtils){
            synchronized (TimeUtils.class){
                if (null== timeUtils){
                    timeUtils = new TimeUtils();
                }
            }
        }
        String timeStr = FORMATTIME.format(timeUtils);
        System.out.println(timeStr);

        return timeStr;

    }

}
