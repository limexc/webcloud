package cn.limexc.controller;


import cn.limexc.entity.User;
import cn.limexc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @RequestMapping("listuser")
    @ResponseBody
    public List<User> listUser(){
        List<User> users = userService.listUser();
        return users;
    }
}
