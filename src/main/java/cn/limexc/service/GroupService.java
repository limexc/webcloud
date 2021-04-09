package cn.limexc.service;

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
}
