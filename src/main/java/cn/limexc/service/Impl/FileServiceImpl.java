package cn.limexc.service.Impl;

import cn.limexc.dao.FileDao;
import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;
import org.springframework.stereotype.Service;

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
    public int addFile(FileModel file, UserFile userFile, User user) {
        return 0;
    }

    @Override
    public int addFile(UserFile userFile, User user) {
        return 0;
    }

    @Override
    public FileModel getFileInfoByMd5(String md5) {
        return null;
    }

    @Override
    public FileModel getFileInfoById(int fid) {
        return null;
    }

    @Override
    public List<UserFile> listUserFile(Integer id, String page, String limit) {
        return fileDao.selectFileListLimit(id,page,limit);
    }

    @Override
    public int UserFileCount(User user) {
        return fileDao.selectCount(user);
    }
}
