package cn.limexc.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


public class MailUtils {
    String toMail;
    String subject;
    String text;

    static String postname="咸闲贤鱼";
    static String host="smtp.mxhichina.com";
    static String sendUser="notice@limexc.cn";
    static String password="**********";


    public MailUtils(String toMail, String subject, String text) {
        this.toMail = toMail;
        this.subject = subject;
        this.text = text;
    }

    private static Session getSysInfo(){

//        InputStream inStream =MailUtils.class.getResourceAsStream("mail.properties");
//        Properties properties = new Properties();
//
//        try {
//            properties.load(inStream);
//            host= properties.getProperty("mail.host");
//            sendUser=properties.getProperty("mail.sendUser");
//            password= properties.getProperty("mail.password");
//            postname= properties.getProperty("mail.postname");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        // 获取系统属性
        Properties sysInfo = System.getProperties();
        // 设置邮件服务器
        sysInfo.setProperty("mail.smtp.host", host);
        sysInfo.put("mail.smtp.auth", "true");

        //sysInfo.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        //sysInfo.put("mail.smtp.socketFactory.port", "465");
        //sysInfo.put("mail.smtp.port", "465");


//        // 关于QQ邮箱，还要设置SSL加密，加上以下代码即可
//        MailSSLSocketFactory qq_ssl = null;
//        try {
//            qq_ssl = new MailSSLSocketFactory();
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        }
//        qq_ssl.setTrustAllHosts(true);
//        sysInfo.put("mail.smtp.ssl.enable", "true");
//        sysInfo.put("mail.smtp.ssl.socketFactory", qq_ssl);


        // 获取默认session对象
        // 构建授权信息，用于进行SMTP进行身份验证
        Session session = Session.getDefaultInstance(sysInfo,new Authenticator(){
            @Override
            public PasswordAuthentication getPasswordAuthentication()
            {//发件人邮件用户名、授权码
                return new PasswordAuthentication(sendUser, password);
            }
        });
        return session;
    }

    public boolean sendMail(){
        try{
            String nick;
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(getSysInfo());
            nick=javax.mail.internet.MimeUtility.encodeText(postname);
            // Set From: 头部头字段
            // message.setFrom(new InternetAddress(sendUser));
            message.setFrom(new InternetAddress(nick+" <"+sendUser+">"));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMail));
            // Set Subject: 头部头字段
            message.setSubject(subject);
            // 设置消息体
            // message.setText(text);
            // 设置 HTML消息
            message.setContent(text,"text/html;charset=UTF-8");
            // 发送消息
            Transport.send(message);
            System.out.println("["+TimeUtils.getUtils().getForMatTime()+"] 向用户："+toMail+" 发送邮件成功");
            return true;
        }catch (MessagingException | UnsupportedEncodingException mex) {
            mex.printStackTrace();
            return false;
        }
    }

}
