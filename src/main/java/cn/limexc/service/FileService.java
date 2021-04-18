package cn.limexc.service;

import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface FileService {

    //列出所有文件
    List<FileModel> listAllFile();

    //列出用户的文件
    List<UserFile> listUserFile(User user);

    //通过筛选列出用户文件
    List<UserFile> listUserFileByType(User user,String type);

    //修改虚拟文件名
    int reName(UserFile userFile,User user);

    //通过文件名查询文件或文件夹
    List<UserFile> selectFiles(Integer id,String key);


    //创建虚拟文件夹
    Map<String,Object> mkDir(String newpath, String name, int page, User user);

    //删除虚拟文件夹，涉及删除文件夹下的文件
    int rmDirOrFile(UserFile userFile,User user);


    //添加未上传过的文件,基础文件不涉及虚拟命名等问题
    int addFile(FileModel file);

    //添加已经有用户上传过的文件，涉及到虚拟命名
    int addVFile(UserFile userFile);

    //通过MD5获取文件信息
    FileModel getFileInfoByMd5(String md5);

    //通过文件ufid获取文件路径及虚拟文件名信息
    FileModel getFileInfoByUFid(String ufid);

    //通过文件ufid获取userfile信息
    UserFile getUFInfoByUFid(Integer ufid);

    //用户文件分页查询
    List<UserFile> listUserFile(Integer id, String page, String limit);

    //返回用户文件的个数
    int UserFileCount(User user);

    //返回用户文件的总大小
    BigInteger sumUserFileSize(User user);

    //返回用户空间的一些信息，暂定使用map
    Map<String,Object> userStorage(User user);
}
