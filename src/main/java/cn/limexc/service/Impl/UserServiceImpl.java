package cn.limexc.service.Impl;

import cn.limexc.dao.UserDao;
import cn.limexc.model.User;
import cn.limexc.service.GroupService;
import cn.limexc.service.UserService;
import cn.limexc.util.TimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    //引用类型自动注入
    @Resource
    private UserDao userDao;

    @Resource
    private GroupService groupService;

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
    //用户基本信息
    @Override
    public User userinfo(Integer id) {
        return userDao.selectUserInfoById(id);
    }

    //除密码外所有信息
    @Override
    public User userallinfo(Integer id) {
        return userDao.selectUserAllInfoById(id);
    }

    @Override
    public Boolean haveUserByName(String name) {
        //查找到了就返回false
        if (userDao.selectUserByUsername(name)!=null){
            return false;
        }
        return true;
    }

    @Override
    public Boolean haveUserByEmail(String email) {
        if (userDao.selectUserByEmail(email)!=null){
            return false;
        }
        return true;

    }

    @Override
/*    @Transactional(
            propagation= Propagation.REQUIRED,
            isolation= Isolation.DEFAULT,
            readOnly=false,
            rollbackFor={
                NullPointerException.class,
            }
    )*/
    public Integer regUser(User user) {
        user.setId(userDao.selectMaxId()+1);
        //默认0为启用账户
        user.setStatus(0);
        //默认新注册用户为普通用户
        user.setGid(2);
        user.setCreate_at(TimeUtils.getUtils().getForMatTime());
        //默认注册时分配空间为0
        user.setStorage(BigInteger.valueOf(0));
        user.setAlisa("null");
        System.out.printf(TimeUtils.getUtils().getForMatTime());
        Integer s =userDao.insertUser(user);
        Boolean isOk = groupService.addUserInGroup(user.getId(), user.getGid());
        if (s==1 && isOk){
            return 1;
        }else{
            return 0;
        }

    }

    @Override
    public Integer findpasswd(Integer id, String pwd) {
        return userDao.updateUserPasswd(id, pwd);
    }

    @Override
    public Integer resetpasswd(Integer id, String old_pwd,String new_pass) {
        Boolean isSame = userDao.selectPwdById(id,old_pwd);
        System.out.println("开始修改密码：isSame为:"+isSame);
        if (isSame){
            return userDao.updateUserPasswd(id, new_pass);
        }
        return 0;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.selectUserByEmail(email);
    }

    @Override
    public Integer updateImage(String profile, Integer uid) {
        return userDao.updateProFile(profile, uid);
    }
}
