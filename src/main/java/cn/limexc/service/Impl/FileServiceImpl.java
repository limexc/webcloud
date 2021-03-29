package cn.limexc.service.Impl;

import cn.limexc.dao.FileDao;
import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;
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
            UserFile uf =fileDao.selectUserFileById(userFile);
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

    @Override
    public FileModel getFileInfoByFid(int fid) {
        return null;
    }

    @Override
    public List<UserFile> listUserFile(Integer id, String page, String limit) {
        //获取文件列表时需要判断目录vpath以及用户是否自定义名称vname

        return fileDao.selectFileListLimit(id,page,limit);
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
        //先查询该用户的所有文件
        List<UserFile> ufs = fileDao.selectFileList(user);
        //循环，查找同一文件夹下下的文件，删除 | 让文件的vpath唯一 感觉可以和删除文件方法合并。
        for (int i=0;i<ufs.size();i++){
            UserFile temp = ufs.get(i);
            String path = temp.getVpath();
            if (temp.getVpath().startsWith(userFile.getVpath())){
                System.out.println("要删除的目录或文件"+path);
                sum += fileDao.deleteUserFile(temp);
            }
        }
        return sum;
    }





}
