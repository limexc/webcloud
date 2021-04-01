package cn.limexc.controller;

import cn.limexc.model.User;
import cn.limexc.service.UserService;
import cn.limexc.util.MailUtils;
import cn.limexc.util.StrMd5Utils;
import cn.limexc.util.TimeUtils;
import cn.limexc.util.VeCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主要要做用户的验证登录注册找回密码等还未登录，以及登录操作【看看以后把登录拿出来】。
 * 用户退出暂时例外，因为还没弄好。
 * index      index页面转跳用，我也不知道为什么写的这东西了。
 * userlogin  用户登录
 * register   用户注册
 * findPassword  密码找回
 * sendMail      邮件发送
 * logOut        用户退出
 */


@Controller
@RequestMapping(value = "/")
public class AccountController {

    @Resource
    private UserService userService;

    private User user = null;

    //处理直接访问的根请求
    @RequestMapping(value = "")
    public String index(){
        return "forward:/login.jsp";
    }

    @RequestMapping(value = "/system/login")
    public String userlogin(HttpServletRequest request, Model model, HttpSession session){
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


        if (rs && password!=null && password!=""){

            System.out.println("邮箱："+email+" 密码："+password+" 邮箱格式："+rs);
            //将查询到的数据放到user中
            user = userService.login(email,password);
            if (user!=null){
                System.out.println("用户详细信息："+user.toString());
                System.out.println(StrMd5Utils.MD5(user.getPassword()));
                //mv.setViewName("redirect:/index.jsp");
                //httpSession.setAttribute("id","");

                //将用户登陆数据保存到session中
                session.setAttribute("user",user);
                session.setAttribute("status",user.getStatus());
                //存储用户的权限？没有实体类对应啊？
                //session.setAttribute("power",);

                return "redirect:/user/main";
            }
            return "forward:/login.jsp";
        }else {
            model.addAttribute("msg","登录失败，请检查用户名密码");
            return "forward:/login.jsp";
        }

    }

    @RequestMapping(value = "/register")
    public String register(){
        return "forward:/register.jsp";
    }


    @RequestMapping(value = "/system/resetpwd")
    public ModelAndView findPassword(){
        ModelAndView mv = new ModelAndView();


        return mv;

    }

    /**
     * 还没测试，准备睡觉了功能还没搞，大体就这样发送
     * 明天再改改，不应该这样写
     * @param req
     */
    @RequestMapping(value = "/system/sendmail",method = RequestMethod.POST)
    @ResponseBody
    public void sendMail(HttpServletRequest req){

        String email = req.getParameter("email");

        StringBuffer text=new StringBuffer("<h1>来自CloudWeb的验证码</h1>");
        text.append("<h3>用户："+email+" 您好</h3>");
        text.append("本次的验证码为: <h4>"+ new VeCodeUtils().getVeCode(email) + "</h4>");
        text.append("<p>如果您没有在<a href=\"#\">小贤网盘</a>注册账号或更改您的账号信息，请忽略此电子邮件。</p></br>" +
                "<h5>验证码有效时间为10分钟</h5>");
        text.append("<p>不要把链接给任何人。</p></br>" +
                "<p>此邮件由系统自动发送，请勿回复此邮件。</p></br></br>" +
                "<p style=\"disable:block; left:60px;\">咸闲贤鱼</p></br>"+
                "<p>"+TimeUtils.getUtils().getForMatTime()+"</p>"
        );
        MailUtils mail = new MailUtils(email,"验证码",text.toString());
        mail.sendMail();
    }

    @RequestMapping(value = "/system/logout")
    public String logOut(HttpSession session){
        //清除session
        session.invalidate();
        return "redirect:/login.jsp";
    }

}
