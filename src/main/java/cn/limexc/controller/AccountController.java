package cn.limexc.controller;

import cn.limexc.model.User;
import cn.limexc.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
        mv.setViewName("redirect:/index.jsp");
        return mv;
    }

    @RequestMapping(value = "/resetpwd")
    public ModelAndView findPassword(){
        ModelAndView mv = new ModelAndView();


        return mv;

    }

}
