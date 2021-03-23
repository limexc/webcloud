package cn.limexc.service;

import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileService {

    //列出所有文件
    List<FileModel> listAllFile();

    //列出用户的文件
    List<UserFile> listUserFile(User user);

    //修改虚拟文件名


    //添加未上传过的文件
    int addFile(FileModel file,UserFile userFile,User user);

    //添加已经有用户上传过的文件
    int addFile(UserFile userFile,User user);

    //通过MD5获取文件信息
    FileModel getFileInfoByMd5(String md5);

    //通过文件fid获取文件信息
    FileModel getFileInfoById(@Param("fid") int fid);







}
