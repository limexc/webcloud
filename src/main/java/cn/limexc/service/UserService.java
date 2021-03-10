package cn.limexc.service;

import cn.limexc.model.User;

import java.util.List;

public interface UserService {
    List<User> listUser();
    User login();
}
