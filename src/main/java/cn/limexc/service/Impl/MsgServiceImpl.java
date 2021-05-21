package cn.limexc.service.Impl;

import cn.limexc.dao.MassageDao;
import cn.limexc.model.Massage;
import cn.limexc.model.User;
import cn.limexc.service.MsgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-05-18 14:14
 * @since jdk1.8.0
 */
@Service
public class MsgServiceImpl implements MsgService {
    @Resource
    private MassageDao msgDao;

    @Override
    public List<Massage> listMsg() {
        return msgDao.selectAllMsg();
    }

    @Override
    public List<Massage> listMsgByType(Integer type) {
        return msgDao.selectMsgByType(type);
    }


    @Override
    public List<Massage> listUserMsg(User user) {
        return msgDao.selectUserMsg(user);
    }

    @Override
    public Massage getMsg(User user, Integer mid) {
        return msgDao.selectMsgByMid(user,mid);
    }

    @Override
    public Massage getMsgAdmin(Integer mid) {
        return msgDao.selectMsgByMidAdmin(mid);
    }

    @Override
    public Boolean createMsg(Massage msg) {
        Boolean isOk=false;
        Integer s = msgDao.insertMsg(msg);
        if (s == 1){
            isOk = true;
        }
        return isOk;
    }

    @Override
    public Boolean upMsg(Massage msg) {
        Boolean isOk=false;
        Integer s = msgDao.updateMsg(msg);
        if (s == 1){
            isOk = true;
        }
        return isOk;
    }

    @Override
    public Boolean delMsg(User user, Integer mid) {
        Boolean isOk=false;
        Integer s = msgDao.deleteMsg(user, mid);
        if (s == 1){
            isOk = true;
        }
        return isOk;
    }
}
