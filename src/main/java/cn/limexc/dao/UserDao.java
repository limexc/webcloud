package cn.limexc.dao;

import cn.limexc.entity.User;

import java.util.List;

public interface UserDao {
    List<User> selectUserList();
}
