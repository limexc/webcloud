package cn.limexc.dao;


import cn.limexc.model.Group;
import org.apache.ibatis.annotations.Param;

public interface GroupDao {

    //查询所有用户组

    //通过uid查询某一用户的组
    Group selectGroupByUid(@Param("uid")Integer uid);

}
