package cn.limexc.service.Impl;

import cn.limexc.dao.FileDao;
import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;

import javax.annotation.Resource;
import java.util.List;

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
}
