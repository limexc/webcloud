package cn.limexc.util;

import java.io.File;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-04-22 14:48
 * @since jdk1.8.0
 */
public class DiskSize {


    public String getTotal(String path){
        File win = new File(path);
        String totalStr=null;
        if (win.exists()) {
            //总空间
            long total = win.getTotalSpace();
            totalStr = new ByteUnitConversion().readableFileSize(total);
            System.out.println("总空间："+totalStr);
        }
        return totalStr;
    }

    public String getUsableSpace(String path){
        File win = new File(path);
        String usableSpaceStr = null;
        if (win.exists()) {
            //可用空间
            long usableSpace = win.getUsableSpace();
            usableSpaceStr = new ByteUnitConversion().readableFileSize(usableSpace);
            System.out.println("可用空间： "+ usableSpaceStr);
        }
        return usableSpaceStr;
    }

}
