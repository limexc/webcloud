package cn.limexc.dao;

import cn.limexc.model.User;

import java.util.List;

public interface UserDao {
    List<User> selectUserList();
}
