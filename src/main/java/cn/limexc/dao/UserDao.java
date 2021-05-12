package cn.limexc.dao;

import cn.limexc.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
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
     * 查询表内最大的ID
     * @return id
     */
    int selectMaxId();

    /**
     * 获取数据库内用户的行数，用来为系统信息页展示提供数据
     * @return
     */
    @Select("SELECT COUNT(*) FROM users")
    int selectUserRow();
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
    @Select("select id,username,email,alisa,status from users where id=#{id}")
    User selectUserInfoById(@Param("id") Integer id);

    @Select("select id,username,email,status from users where username=#{name}")
    User selectUserByUsername(@Param("name") String name);

    @Select("select id,username,email,status from users where email=#{email} ")
    User selectUserByEmail(@Param("email") String email);

    //查询用户的所有信息
    @Select("select users.id AS id,users.username AS username,users.email AS email,users.alisa AS alisa," +
            "users.storage AS storage,users.status AS status,users.create_at AS create_at,users.delete_at AS delete_at," +
            "users.profile AS profile,user_group.gid AS gid " +
            "from (users LEFT JOIN user_group ON users.id=user_group.uid ) where users.id=#{id}")
    User selectUserAllInfoById(@Param("id") Integer id);

    //id与前端传入的用户密码进行判断是否一致
    @Select("SELECT CASE   WHEN `password`=#{pwd} THEN 'TRUE' ELSE 'FALSE' END AS isSame " +
            "FROM users WHERE id=#{id}")
    Boolean selectPwdById(@Param("id") Integer id,@Param("pwd") String pwd);


    //根据UID更新密码
    @Update("UPDATE users SET `password`=#{pwd} WHERE id=#{id}")
    Integer updateUserPasswd(@Param("id")Integer id,@Param("pwd")String pwd);

    //根据uid更新头像
    @Update("UPDATE users SET `profile`=#{profile} WHERE id = #{uid}")
    Integer updateProFile(@Param("profile") String profile,@Param("uid") Integer uid);

    //查询注册时间前 top  的用户
    @Select("SELECT id,username,email,create_at, storage FROM users ORDER BY create_at DESC LIMIT ${top}")
    List<User> selectNewUser(@Param("top") String top);

    //更新用户状态字段
    @Update("UPDATE users SET `status`=#{status} WHERE id=#{uid}")
    Integer updateUserStatus(@Param("status")Integer status,@Param("uid") Integer uid);

    //更新用户存储空间字段
    @Update("UPDATE users SET `storage`=#{storage} WHERE id=#{uid}")
    Integer updateUserStorage(@Param("storage")long storage,@Param("uid") Integer uid);
    //更新用户名字段
    @Update("UPDATE users SET `username`=#{name} WHERE id=#{uid}")
    Integer updateUserName(@Param("name")String name,@Param("uid") Integer uid);

    //删除用户数据
    @Delete("DELETE FROM users WHERE id=#{user.id}")
    Integer deleteUser(@Param("user") User user);

}
