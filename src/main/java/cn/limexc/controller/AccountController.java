package cn.limexc.controller;

import cn.limexc.model.User;
import cn.limexc.service.UserService;
import cn.limexc.util.MailUtils;
import cn.limexc.util.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AccountController {

    @Resource
    private UserService userService;

    private User user;

    @RequestMapping(value = "/login")
    public ModelAndView userlogin(HttpServletRequest request){
        String username;
        String password;
        ModelAndView mv = new ModelAndView();

        //总是傻了吧唧的，别忘了，暂存。
        //req.setCharacterEncoding("UTF-8");
        //resp.setContentType("text/html;charset=UTF-8");

        username = request.getParameter("account");
        password = request.getParameter("password");

        //正则表达式判断是否为邮箱  --- 可以抽取放在util中
        String mailcheck = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        // 邮箱验证规则
        // 编译正则表达式
        Pattern pattern = Pattern.compile(mailcheck);
        Matcher matcher = pattern.matcher(username);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();




        System.out.println("userlogin   "+username+"  "+password+"  "+rs);
        //mv.setViewName("redirect:/index.jsp");
        mv.setViewName("forward:/index.jsp");
        return mv;
    }

    @RequestMapping(value = "/resetpwd")
    public ModelAndView findPassword(){
        ModelAndView mv = new ModelAndView();


        return mv;

    }

    /**
     * 还没测试，准备睡觉了功能还没搞，大体就这样发送
     * 明天再改改，不应该这样写
     * @param req
     */
    @RequestMapping(value = "/sendmail")
    @ResponseBody
    public void sendMail(HttpServletRequest req){
        String email = req.getParameter("email");

        System.out.println("["+TimeUtils.timeUtils.getForMatTime()+"]"+"获取到用户输入的邮箱： "+email);

        StringBuffer text=new StringBuffer("<h1>来自CloudWeb的验证码</h1>");
        text.append("<h3>用户："+email+" 您好</h3>");
        text.append("本次的验证码为");
        text.append("<p>如果您没有在注册账号或更改您的账号信息，请忽略此电子邮件。</p></br>");
        text.append("<p>不要把链接给任何人。</p></br>" +
                "<p>此邮件由系统自动发送，请勿回复此邮件。</p></br></br>" +
                "<p>咸闲贤鱼</p></br>" +
                TimeUtils.timeUtils.getForMatTime());
        MailUtils mail = new MailUtils(email,"验证码",text.toString());
        mail.sendMail();
    }

}
