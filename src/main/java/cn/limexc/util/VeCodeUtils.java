package cn.limexc.util;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-03-20 17:30
 * @since jdk1.8.0
 */
public class VeCodeUtils {

    /**
     * 验证码生成太简单了...肯定不行，以后再改
     * 先把功能实现了再说
     *
     * 想法1：加一个自定义混淆码  实现简单。
     *
     */

    public String getVeCode(String email){
        String time = TimeUtils.getUtils().getForMatTime();
        time = time.substring(0,15);

        System.out.println(time);
        String args = email+time;

        return StrMd5Utils.MD5(args).substring(0,6);
    }

}
