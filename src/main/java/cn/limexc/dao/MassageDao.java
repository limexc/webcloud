package cn.limexc.dao;

import cn.limexc.model.Massage;
import cn.limexc.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-05-18 14:15
 * @since jdk1.8.0
 */
@Repository
public interface MassageDao {
    //列出所有请求
    @Select("SELECT id,uid,title,info,size,create_time,reply,status FROM massage")
    List<Massage> selectAllMsg();

    //列出所有请求
    @Select("SELECT id,uid,title,info,size,create_time,reply,status FROM massage WHERE status=#{type}")
    List<Massage> selectMsgByType(@Param("type") Integer type);

    //列出某个用户的请求
    @Select("SELECT id,uid,title,info,size,create_time,reply,status FROM massage WHERE uid=#{user.id}")
    List<Massage> selectUserMsg(@Param("user") User user);

    //用户获得某个msg请求
    @Select("SELECT id,uid,title,info,size,create_time,reply,status FROM massage WHERE uid=#{user.id} AND id=#{mid}")
    Massage selectMsgByMid(@Param("user") User user,@Param("mid") Integer mid);

    @Select("SELECT id,uid,title,info,size,create_time,reply,status FROM massage WHERE id=#{mid}")
    Massage selectMsgByMidAdmin(@Param("mid") Integer mid);

    //向数据库内写入msg
    @Insert("INSERT INTO massage (uid,title,info,size,create_time,status) " +
            "VALUES(#{msg.uid},#{msg.title},#{msg.info},#{msg.size},NOW(),'0')"
    )
    Integer insertMsg(@Param("msg") Massage msg);

    //管理员对某个请求状态进行更新
    @Update("UPDATE massage SET status=#{msg.status},reply=#{msg.reply} WHERE id=#{msg.id}")
    Integer updateMsg(@Param("msg") Massage msg);

    //用户自主删除某个msg
    @Delete("DELETE FROM massage WHERE uid=#{user.id} AND id=#{mid}")
    Integer deleteMsg(@Param("user") User user,@Param("mid") Integer mid);
}
