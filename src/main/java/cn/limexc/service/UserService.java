package cn.limexc.service;

import cn.limexc.model.User;

import java.util.List;

public interface UserService {

    //列出所有用户
    List<User> listUser();

    //用户登录
    User login(String email,String password);

    //用户注册
    Integer regUser(User user);

    //插入用户头像


    //用户信息更新


    //用户修改密码


    //用ID查找用户，返回基本信息
    User userinfo(Integer id);

    //用ID查找用户，返回除密码外所有信息
    User userallinfo(Integer id);

    /**
     * 通过用户名查找用户，用于注册时验证该用户名是否被注册
     * @param name 用户名
     * @return  true表示该用户名可用
     */
    Boolean haveUserByName(String name);


    Boolean haveUserByEmail(String email);


}
