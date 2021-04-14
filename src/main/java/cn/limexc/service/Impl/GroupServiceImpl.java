package cn.limexc.service.Impl;

import cn.limexc.dao.GroupDao;
import cn.limexc.model.Group;
import cn.limexc.service.GroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-04-09 14:51
 * @since jdk1.8.0
 */
@Service
public class GroupServiceImpl implements GroupService {
    @Resource
    private GroupDao groupDao;

    @Override
    public Boolean addUserInGroup(Integer uid, Integer gid) {
        if (groupDao.insterGroupUser(uid,gid)==1){
            return true;
        }
        return false;
    }


    @Override
    public Group getUserGroup(Integer uid) {
        return groupDao.selectGroupByUid(uid);
    }
}
