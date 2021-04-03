package cn.limexc.service.Impl;

import cn.limexc.dao.FileDao;
import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;
import cn.limexc.util.ByteUnitConversion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileDao fileDao;

    @Override
    public List<FileModel> listAllFile() {
        return fileDao.selectAllFileList();
    }

    @Override
    public List<UserFile> listUserFile(User user) {
        return fileDao.selectFileList(user);
    }

    @Override
    public int reName(UserFile userFile,User user) {
        int sum=0;
        List<UserFile> ufs = fileDao.selectFileList(user);
        //判断传入的userFile是目录还是文件，文件的话都有 fid
        if (userFile.getFid()!=null){
            sum = fileDao.updateVname(userFile);



        }else {
            //获取初始的虚拟路径名
            UserFile uf =fileDao.selectUserFileById(userFile.getId());
            String vpath =uf.getVpath();
        for (int i=0;i<ufs.size();i++){
                UserFile temp = ufs.get(i);
                String path = temp.getVpath();
                if (temp.getVpath().startsWith(vpath)){
                    System.out.println("要改名的目录或文件"+path+"--->"+userFile.getVpath());
                    sum += fileDao.updateVpath(userFile);
                }
            }
        }

        return sum;
    }

    @Override
    public int addFile(FileModel file) {
        return fileDao.insertFile(file);
    }

    @Override
    public int addVFile(UserFile userFile) {
        return fileDao.insertUserFile(userFile);
    }

    @Override
    public FileModel getFileInfoByMd5(String md5) {
        return fileDao.selectFileMd5(md5);
    }

    /**
     * 通过ufid【userfile表id】查询需要的信息【文件名，路径】
     * @param ufid userfile表id
     * @return 文件关键信息
     */
    @Override
    public FileModel getFileInfoByUFid(String ufid) {
        return fileDao.getFileInfoByUFid(ufid);
    }

    @Override
    public UserFile getUFInfoByUFid(Integer ufid) {
        return fileDao.selectUserFileById(ufid);
    }

    @Override
    public List<UserFile> listUserFile(Integer id, String page, String limit) {
        //获取文件列表时需要判断目录vpath
        List<UserFile> userFiles = fileDao.selectFileListLimit(id,page,limit);

        //循环修改filsize的值，数据库中存储原始未转换数据方便对用户空间的控制。
        for (UserFile uf:userFiles) {
            String  tmp=null;
            tmp = new ByteUnitConversion().readableFileSize(Long.parseLong(uf.getFilesize()));
            uf.setFilesize(tmp);
        }

        return userFiles;
    }

    @Override
    public int UserFileCount(User user) {
        return fileDao.selectCount(user);
    }



    @Override
    public int mkDir(UserFile userFile) {
        return 0;
    }

    @Override
    public int rmDirOrFile(UserFile userFile,User user) {
        //受影响的行数
        int sum=0;
        //判断是文件夹还是文件
        userFile=fileDao.selectUserFileById(userFile.getId());
        System.out.println("这里是service"+userFile.toString());
        if (userFile.getFid()!=null){
            return sum=fileDao.deleteUserFile(userFile);
        }


        //查询该用户的所有文件
        List<UserFile> ufs = fileDao.selectFileList(user);
        //循环，查找同一文件夹下下的文件，删除 | 让文件的vpath唯一 感觉可以和删除文件方法合并。
        for (int i=0;i<ufs.size();i++){
            UserFile temp = ufs.get(i);
            String path = temp.getVpath();
            if (temp.getVpath().startsWith(userFile.getVpath())){
                System.out.println("要删除的目录"+path);
                sum += fileDao.deleteUserFile(temp);
            }
        }
        return sum;
    }





}
