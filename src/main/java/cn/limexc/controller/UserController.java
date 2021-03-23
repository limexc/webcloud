package cn.limexc.controller;


import cn.limexc.model.User;
import cn.limexc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 主要做登录后用户信息的修改查询等操作。
 *
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/listUser")
    @ResponseBody
    public List<User> listUser(){
        List<User> users = userService.listUser();
        return users;
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test(){
        System.out.println(new Date()+"访问到了");
    }


    @RequestMapping(value = "/main")
    public String index(HttpSession session){


        return "forward:/index.jsp";
    }

}
