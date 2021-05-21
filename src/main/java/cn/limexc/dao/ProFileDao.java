package cn.limexc.dao;

import cn.limexc.model.ProFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-04-21 23:36
 * @since jdk1.8.0
 */
@Repository
public interface ProFileDao {
    //向数据库插入数据
    @Insert("INSERT INTO `profile`(name,path,url) VALUES (#{name},#{path},#{url})")
    Integer insertProFile(@Param("name")String name,@Param("path") String path,@Param("url") String url);
    //通过url参数获得profile
    @Select("SELECT * FROM `profile` WHERE url=#{url}")
    ProFile selectProFileUrl(@Param("url") String url);

}
