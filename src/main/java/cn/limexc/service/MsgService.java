package cn.limexc.service;

import cn.limexc.model.Massage;
import cn.limexc.model.User;

import java.util.List;

public interface MsgService {

    //列出所有请求
    List<Massage> listMsg();

    List<Massage> listMsgByType(Integer type);

    //列出某个用户的请求
    List<Massage> listUserMsg(User user);

    //用户获得某个msg请求
    Massage getMsg(User user,Integer mid);

    Massage getMsgAdmin(Integer mid);

    //向数据库内写入msg
    Boolean createMsg(Massage msg);

    //管理员对某个请求状态进行更新
    Boolean upMsg(Massage msg);

    //用户自主删除某个msg
    Boolean delMsg(User user,Integer mid);

}
