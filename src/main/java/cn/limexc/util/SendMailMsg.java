package cn.limexc.util;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-05-21 19:57
 * @since jdk1.8.0
 */
public class SendMailMsg {
    public Boolean mailMsg(String email) {
        String title = "来自网盘的通知";

        StringBuffer text = new StringBuffer();
        text.append("<h3>用户：" + email + " 您好</h3>");
        text.append("<p>您在网盘的信息已经修改，请登录查看</p><br>");
        text.append("<p>此邮件由系统自动发送，请勿回复此邮件。</p></br></br>" +
                "<p style=\"disable:block; left:60px;\">咸闲贤鱼</p></br>" +
                "<p>" + TimeUtils.getUtils().getForMatTime() + "</p>"
        );
        MailUtils mail = new MailUtils(email, title, text.toString());

        //System.out.printf(msg.toString());
        ResultData rd = new ResultData();

        return mail.sendMail();

    }
}
