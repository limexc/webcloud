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

    //列出所有用户
    @Override
    public List<User> listUser() {
        return userDao.selectUserList();
    }

    //实现用户登录
    @Override
    public User login(String email,String password) {
        return userDao.selectForEmailAndPasswd(email,password);
    }
}
