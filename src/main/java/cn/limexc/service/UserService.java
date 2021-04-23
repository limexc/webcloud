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

    //用户信息更新


    //用户修改密码|未登录
    Integer findpasswd(Integer id,String pwd);

    //通过邮箱查找用户
    User getUserByEmail(String email);

    //用户修改密码|已登陆
    Integer resetpasswd(Integer id,String old_pwd,String new_pass);


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

    /**
     * 通过邮箱查找用户，用来做找回密码的验证
     * @param email 邮箱
     * @return  是否
     */
    Boolean haveUserByEmail(String email);

    /**
     * 修改用户的 头像  地址
     * @param profile 头像地址
     * @param uid     用户 id
     * @return        影响的行数
     */
    Integer updateImage(String profile,Integer uid);

    /**
     * 查询前 top 个用户基本信息，用于系统信息页展示
     * @param top 数字
     * @return    列表
     */
    List<User> getNewUser(String top);

    /**
     * 获取users表中 用户的行数
     * @return  行数
     */
    int getUserRow();

}
