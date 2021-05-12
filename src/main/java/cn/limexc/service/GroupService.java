package cn.limexc.service;

import cn.limexc.model.Group;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-04-09 14:34
 * @since jdk1.8.0
 */
public interface GroupService {

    /**
     * 向用户组内添加用户
     * @param uid 用户id
     * @param gid 组id
     * @return 插入是否成功
     */
    Boolean addUserInGroup(Integer uid,Integer gid);

    /**
     * 获取用户所在的组信息
     * @param uid uid
     * @return group信息
     */
    Group getUserGroup(Integer uid);

    /**
     * 转换用户所在组
     * @param uid 用户id
     * @return    true or false
     */
    Boolean changeUserGroup(Integer uid);
}
