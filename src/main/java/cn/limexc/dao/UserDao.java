package cn.limexc.dao;

import cn.limexc.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    List<User> selectUserList();
    User selectForEmailAndPasswd(@Param(value = "email") String email, @Param(value = "password") String password);
}
