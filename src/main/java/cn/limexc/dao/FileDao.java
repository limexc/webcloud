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
    List<UserFile> selectFileList(User user);

    /**
     * 通过md5查询文件是否存在
     * @param md5 md5值
     * @return    返回文件信息
     */
    FileModel selectFileMd5(@Param("md5") String md5);


    /**
     * 文件信息插入数据库
     * @param file 文件信息
     */
    void insertFile(FileModel file);

}
