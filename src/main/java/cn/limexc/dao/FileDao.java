package cn.limexc.dao;

import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * 通过md5查询文件是否存在
     * @param md5 md5值
     * @return    返回文件信息
     */
    FileModel selectFileMd5(@Param("md5") String md5);


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


    List<UserFile> selectFilesByKey(@Param("uid")Integer uid,@Param("key")String key);
}
