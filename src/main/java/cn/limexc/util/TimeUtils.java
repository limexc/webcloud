package cn.limexc.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtils {
    /**
     * 不知道怎么想的了，先这样吧，后期改单例模式会好一些   大概？
     * @return  返回格式化后的时间
     */
    public String getForMatTime(){
        SimpleDateFormat formatime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        System.out.println(formatime.format(new Date().getTime()));
        return formatime.format(new Date().getTime());
    }

}
