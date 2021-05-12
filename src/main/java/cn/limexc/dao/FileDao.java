package cn.limexc.dao;

import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FileDao {
    /**
     * 获取当前全部的文件列表
     * @return 返回【全部文件】列表
     */
    List<FileModel> selectAllFileList();


    /**
     * 获取文件列表，用于查询用户的文件列表
     * @param user 用户信息
     * @return  返回【用户文件】列表
     */
    List<UserFile> selectFileList(@Param("user") User user);

    /**
     * 获取文件列表，用于查询用户的文件列表   ?page=1&limit=30
     * @param id 用户信息
     * @return  返回【用户文件】列表
     *
     */
    List<UserFile> selectFileListLimit(@Param("id") Integer id,@Param("page") String page,@Param("limit") String limit);

    /**
     * 通过文件类型查询文件列表
     * @param user 用户信息
     * @param type 文件类型（file.filetype的前半部分）
     * @return 返回【用户文件】列表
     */
    @Select("SELECT file.filesize AS filesize, user_file.vfname AS vfname, user_file.vpath AS vpath," +
            "user_file.id AS id, user_file.uptime AS uptime, file.filetype AS filetype " +
            "FROM ( user_file LEFT JOIN users ON users.id = user_file.uid ) LEFT JOIN file ON file.id = user_file.fid  " +
            "WHERE users.id = #{user.id} AND file.filetype LIKE #{type}")
    List<UserFile> selectFileByType(@Param("user") User user,@Param("type")String type);

    /**
     * 通过md5查询文件是否存在
     * @param md5 md5值
     * @return    返回文件信息
     */
    FileModel selectFileMd5(@Param("md5") String md5);

    /**
     * 获取文件的行数
     * @return  行数
     */
    @Select("SELECT COUNT(*) FROM file")
    int selectFileRow();

    /**
     * 获取当前系统 文件总大小
     * @return  总大小
     */
    @Select("SELECT SUM(filesize) FROM file")
    long selectAllFileSize();


    /**
     * 新文件信息插入数据库
     * @param file 文件信息
     * @return 影响行数
     */
    int insertFile(@Param("file") FileModel file);

    /**
     * 将文件信息写入userfile表
     * @param userFile userfile信息
     * @return 影响行数
     */
    int insertUserFile(@Param("userFile")UserFile userFile);


    /**
     * 获取当前用户文件的总数，用于分页。
     * @param user 用户信息
     * @return 文件个数
     */
    int selectCount(@Param("user") User user);

    /**
     * 用户删除文件
     * @param userFile 用户文件信息
     * @return  影响的行数
     */
    int deleteUserFile(@Param("userFile")UserFile userFile);

    /**
     * 管理员删除文件
     * @param fid 文件信息
     * @return  影响的行数
     */
    @Delete("DELETE FROM file WHERE id=#{fid}")
    int deleteFile(@Param("fid")String fid);

    @Update("UPDATE user_file SET status =#{userFile.status} WHERE id=#{userFile.id}")
    int updateUFileStatus(@Param("userFile")UserFile userFile);

    /**
     * 更新、修改文件或目录名称【虚拟】
     * @param userFile 用户文件啊
     * @return
     */
    int updateVnameAndVpath(@Param("userFile")UserFile userFile);


    /**
     * 通过userfile表的id进行查找
     * @param id
     * @return
     */
    UserFile selectUserFileById(@Param("id")Integer id);

    FileModel getFileInfoByUFid(@Param("ufid")String ufid);

    /**
     *
     * @param fid
     * @return
     */
    @Select("SELECT realpath,filename FROM file WHERE id=#{fid}")
    FileModel getFileInfoByFid(@Param("fid")String fid);

    /**
     *
     * @param uid
     * @param key
     * @return
     */
    List<UserFile> selectFilesByKey(@Param("uid")Integer uid,@Param("key")String key);

    //用户文件大小和，用来做容量的控制
    @Select("SELECT SUM(file.filesize) AS size " +
            "FROM  (user_file LEFT JOIN users ON users.id=user_file.uid)LEFT JOIN file ON file.id=user_file.fid " +
            "WHERE users.id = #{id}")
    String sumUserFileSize(@Param("id") Integer id);
}
