package cn.limexc.service.Impl;

import cn.limexc.dao.UserDao;
import cn.limexc.model.User;
import cn.limexc.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    //引用类型自动注入
    @Resource
    private UserDao userDao;

    @Override
    public List<User> listUser() {
        return userDao.selectUserList();
    }

    @Override
    public User login() {
        return userDao.selectForNameAndPasswd();
    }
}
