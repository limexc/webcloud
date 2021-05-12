package cn.limexc.dao;


import cn.limexc.model.Group;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupDao {

    //查询所有用户组

    //通过uid查询某一用户的组
    @Select("SELECT group.id AS id,group.name AS name,group.power AS power " +
            "FROM `group`,user_group,users WHERE group.id=user_group.gid " +
            "AND user_group.uid=users.id AND  users.id=#{uid}")
    Group selectGroupByUid(@Param("uid")Integer uid);

    //向user-group表中插入数据
    @Insert("insert into user_group(uid,gid) values(#{uid},#{gid})")
    Integer insterGroupUser(@Param("uid")Integer uid,@Param("gid")Integer gid);

    @Update("UPDATE user_group SET gid=#{gid} WHERE uid=#{uid}")
    Integer updateUserGruop(@Param("uid")Integer uid,@Param("gid")Integer gid);
}
