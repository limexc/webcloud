package cn.limexc.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownLoadFile {

    public String  downloadFile(HttpServletResponse rep, File file, String name){
        if (file.exists()) {

            rep.setHeader("Content-Type", "application/x-msdownload");
            rep.setHeader("Content-Disposition", "attachment;filename="+toUTF8String(name));

            //rep.addHeader("Content-Disposition", "attachment;fileName=" + name);// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            ServletOutputStream out;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                out = rep.getOutputStream();
                out.flush();
                int i = bis.read(buffer);
                while (i != -1) {
                    out.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            System.out.println("错误！文件不存在！");
        }
        return null;
    }

    /**
     * 下载保存时中文文件名的字符编码转换方法
     */
    public String toUTF8String(String str) {
        StringBuffer sb = new StringBuffer();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            // 取出字符中的每个字符
            char c = str.charAt(i);
            // Unicode码值为0~255时，不做处理
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else { // 转换 UTF-8 编码
                byte b[];
                try {
                    b = Character.toString(c).getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    b = null;
                }
                // 转换为%HH的字符串形式
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k &= 255;
                    }
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

}
