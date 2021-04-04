package cn.limexc.dao;

import cn.limexc.model.ShareFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ShareFileDao {

    /**
     * 通过uid查询用户分享的全部文件
     * @param uid 用户id
     * @return    该用户分享的全部文件
     */
    @Select("SELECT id,fid,uid,ufid,url,`index`,other FROM sharefile WHERE uid=#{uid}")
    List<ShareFile> selectAllUserShareByUid(@Param("uid") Integer uid);

    /**
     * 通过sharefile的id获取分享页面的链接url
     * 用于用户请求某个地址时进行对文件的查找
     * 用的了这么麻烦吗？吐血，当时搞数据库的时候绝对脑子进水了。
     * @param url 可以算是一个唯一标识，可这个标识用这表的id不香吗？说的好听是为了防止用户瞎蒙，可又有什么关系？
     * @return 用户分享的某个文件信息
     */
    @Select("SELECT id,fid,uid,ufid,url,`index`,other FROM sharefile WHERE url=#{url}")
    ShareFile selectUserShareByUrl(@Param("url") String url);

    /**
     * 向数据库中插入分享文件信息,用于用户分享文件时插入数据。
     * @param shareFile  共享的文件信息
     * @return     影响的行数
     */
    @Insert("INSERT INTO sharefile (id,fid,uid,ufid,url,`index`,other) VALUE (#{shareFile.id},#{shareFile.fid},#{shareFile.uid},#{shareFile.ufid},#{shareFile.url},#{shareFile.index},#{shareFile.other})")
    Integer insertUserShare(@Param("shareFile") ShareFile shareFile);

    /**
     * 通过ufid获取用户分享的文件信息，在最初并没有一个文件只能生成一个分享链接的想法，为了简化，就故意做了一个用户只能对同一文件生成一次分享链接。
     * 如果后面有需要可以进行修改。
     * 【重要】数据库中 userfile表id字段设置了自增，不想太麻烦了，就不设置外键约束了。在用户删除文件时注意处理相关联的共享链接。
     * @param ufid sharefile表中的ufid
     * @return 共享信息
     */
    @Select("SELECT id,fid,uid,url,other,`index`,ufid  FROM sharefile WHERE ufid=#{ufid}")
    ShareFile selectUserShareByUFid(@Param("ufid")Integer ufid);


    /**
     * 获取当前表内最大的id，可用于做id字段的自增。
     * @return 最大id号
     */
    @Select("SELECT max(id) FROM sharefile")
    Integer selectMaxId();

}
