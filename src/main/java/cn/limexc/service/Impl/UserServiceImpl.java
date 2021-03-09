package cn.limexc.service.Impl;

import cn.limexc.dao.UserDao;
import cn.limexc.entity.User;
import cn.limexc.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    @Override
    public List<User> listUser() {
        return userDao.selectUserList();
    }
}
