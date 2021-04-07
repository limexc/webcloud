package cn.limexc.dao;

import cn.limexc.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {
    /**
     * 查询用户列表
     * @return  返回用户列表
     */
    List<User> selectUserList();

    /**
     * 通过邮箱、密码查询信息，用于验证用户是否存在
     * @param email    邮箱
     * @param password 密码
     * @return         用户
     */
    User selectForEmailAndPasswd(@Param(value = "email") String email, @Param(value = "password") String password);

    /**
     * 向数据库插入用户信息
     * @param user  用户信息
     * @return      影响的行数【判断插入是否成功】
     */
    int insertUser(@Param(value = "user") User user);



    /**
     * 查询邮箱数据
     * @param email  邮箱
     * @return       查询到的行数
     */
    @Select("SELECT COUNT(*) FROM users WHERE email=#{email}")
    int selectEmail(@Param("email") String email);

    /**
     * 通过id查找用户，返回基本信息
     * @param id id
     * @return   用户user基本信息
     */
    @Select("select id,username,email,alisa from users where id=#{id}")
    User selectUserInfoById(@Param("id") Integer id);

    @Select("select id,username,email from users where username=#{name}")
    User selectUserByUsername(@Param("name") String name);

    @Select("select id,username,email from users where email=#{email} ")
    User selectUserByEmail(@Param("email") String email);
}
