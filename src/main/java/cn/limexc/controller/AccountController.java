package cn.limexc.controller;

import cn.limexc.model.User;
import cn.limexc.service.UserService;
import cn.limexc.util.MailUtils;
import cn.limexc.util.StrMd5Utils;
import cn.limexc.util.TimeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AccountController {

    @Resource
    private UserService userService;

    private User user = null;

    @RequestMapping(value = "/login")
    public String userlogin(HttpServletRequest request, Model model, HttpSession httpSession){
        String email;
        String password;

        //总是傻了吧唧的，别忘了，暂存。
        //req.setCharacterEncoding("UTF-8");
        //resp.setContentType("text/html;charset=UTF-8");

        email = request.getParameter("account");
        password = request.getParameter("password");

        //正则表达式判断是否为邮箱  --- 可以抽取放在util中
        String mailcheck = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        // 邮箱验证规则
        // 编译正则表达式
        Pattern pattern = Pattern.compile(mailcheck);
        Matcher matcher = pattern.matcher(email);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();

        System.out.println("邮箱："+email+" 密码："+password+" 邮箱格式："+rs);

        //将查询到的数据放到user中
        user = userService.login(email,password);
        System.out.println("用户详细信息："+user.toString());
        System.out.println(StrMd5Utils.MD5(user.getPassword()));

        if (rs && password!=null && password!=""){
            if (user!=null){
                //mv.setViewName("redirect:/index.jsp");
                httpSession.setAttribute("id","");
                return "forward:/index.jsp";
            }
        }else {
            model.addAttribute("msg","登录失败，请检查用户名密码");
            return "forward:/login.jsp";
        }

        return null;

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
