package cn.limexc.service;

import cn.limexc.model.User;

import java.util.List;

public interface UserService {

    //列出所有用户
    List<User> listUser();

    //用户登录
    User login(String email,String password);
}
