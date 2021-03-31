package cn.limexc.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-03-20 17:30
 * @since jdk1.8.0
 */
@PropertySource(value = {"classpath:system.properties"})
public class VeCodeUtils {

    /**
     * 验证码生成太简单了...肯定不行，以后再改
     * 先把功能实现了再说
     *
     * 想法1：加一个自定义混淆码  实现简单。
     *
     */
    @Value("${vecode.confuse}")
    private String tmp;

    public String getVeCode(String email){
        String time = TimeUtils.getUtils().getForMatTime();
        time = time.substring(0,15);
        String args = email+time+tmp;

        return StrMd5Utils.MD5(args).substring(0,6);
    }

}
