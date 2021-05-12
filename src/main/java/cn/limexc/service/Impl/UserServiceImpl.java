package cn.limexc.service.Impl;

import cn.limexc.dao.UserDao;
import cn.limexc.model.User;
import cn.limexc.service.GroupService;
import cn.limexc.service.UserService;
import cn.limexc.util.TimeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public Boolean changeUserName(String name, Integer uid) {
        Boolean isOk = false;
        if (userDao.updateUserName(name,uid)==1){
            isOk=true;
        }
        return isOk;
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
        //默认注册时分配空间为  1G
        user.setStorage("1073741824");
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

    @Override
    public List<User> getNewUser(String top) {
        return userDao.selectNewUser(top);
    }

    @Override
    public int getUserRow() {
        return userDao.selectUserRow();
    }

    @Override
    public Boolean changeUserStatus(Integer uid) {
        Boolean isOk = false;
        Integer tempstatus = userinfo(uid).getStatus();
        if (tempstatus==0){
            userDao.updateUserStatus(1,uid);
            return isOk=true;
        }else if (tempstatus==1){
            userDao.updateUserStatus(0,uid);
            return isOk=true;
        }
        return isOk;
    }

    @Override
    public Boolean changeUserStorage(String  storage, Integer uid) {
        Boolean isOk = false;
        //判断storage
        //去掉字符串中的空格 以及其他非法字符
        //1. 一个正则表达式
        String regExp="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
        //2. 这里是将特殊字符换为空字符串,""代表直接去掉
        String replace = "";
        //3. 要处理的字符串
        storage = storage.replaceAll(regExp,replace);
        storage = storage.replace(" ",replace);
        System.out.println("去除特殊字符后的值："+ storage);
        //转换字符串中的字母全为大写
        storage = storage.toUpperCase();
        //末尾匹配 TB T 、GB G、 MB M、 B
        String regExpTy= "[TGMKB]";
        long longStorage = 0;
        if (storage.endsWith("TB")||storage.endsWith("T")){
            storage = storage.replaceAll(regExpTy,replace);
            longStorage = Long.parseLong(storage)*1024*1024*1024*1024;
        }else if (storage.endsWith("GB")||storage.endsWith("G")){
            storage = storage.replaceAll(regExpTy,replace);
            System.out.println(storage);
            longStorage = Long.parseLong(storage);
            longStorage = longStorage*1024*1024*1024;
        }else if (storage.endsWith("MB")||storage.endsWith("M")){
            storage = storage.replaceAll(regExpTy,replace);
            longStorage = Long.parseLong(storage)*1024*1024;

        }else if (storage.endsWith("KB")||storage.endsWith("K")){
            storage = storage.replaceAll(regExpTy,replace);
            longStorage = Long.parseLong(storage)*1024;

        }else if (storage.endsWith("B")){
            storage = storage.replaceAll(regExpTy,replace);
            longStorage = Long.parseLong(storage);

        }if (userDao.updateUserStorage(longStorage,uid)==1){
            isOk=true;
        }
        return isOk;
    }

    @Override
    public Boolean delUser(User user) {
        Integer i = userDao.deleteUser(user);
        if (i==1){
            return true;
        }
        return false;
    }
}
