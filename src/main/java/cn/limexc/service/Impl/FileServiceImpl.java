package cn.limexc.service.Impl;

import cn.limexc.dao.FileDao;
import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;
import cn.limexc.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        List<UserFile> userFiles = fileDao.selectFileList(user);
        //循环修改filsize的值，数据库中存储原始未转换数据方便对用户空间的控制。
        String  tmp=null;
        for (UserFile uf:userFiles) {
            if (uf.getFilesize()!=null){
                tmp = new ByteUnitConversion().readableFileSize(Long.parseLong(uf.getFilesize()));
                uf.setFilesize(tmp);
            }else {
                uf.setFilesize("-");
            }
        }

        return userFiles;
    }

    @Override
    public int reName(UserFile uf,User user) {
        int sum=0;
        List<UserFile> ufs = fileDao.selectFileList(user);
        UserFile userFile = fileDao.selectUserFileById(uf.getId());
        String vpath = userFile.getVpath();
        //判断传入的userFile是目录还是文件，文件的话都有 fid
        if (userFile.getFid()!=null){
            //文件的话直接改名，同时也要将路径最后的文件名进行修改
            String[] temps = vpath.split("/");
            temps[temps.length-1]= uf.getVfname();
            String tmpath = StringUtils.join(temps, "/");
            uf.setVpath(tmpath);

            System.out.println(uf.toString());
            sum = fileDao.updateVnameAndVpath(uf);

        }else if (userFile!=null&&userFile.getFid()==null){
            //获取初始的虚拟路径名

        for (int i=0;i<ufs.size();i++){
                UserFile temp = ufs.get(i);
                String path = temp.getVpath();

                if (temp.getVpath().startsWith(vpath)){
                    System.out.println("要改名的目录或文件"+path+"--->"+userFile.getVpath());
                    sum += fileDao.updateVnameAndVpath(userFile);
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
        String  tmp=null;
        for (UserFile uf:userFiles) {
            if (uf.getFilesize()!=null){
                tmp = new ByteUnitConversion().readableFileSize(Long.parseLong(uf.getFilesize()));
                uf.setFilesize(tmp);
            }else {
                uf.setFilesize("-");
            }
        }

        return userFiles;
    }

    @Override
    public int UserFileCount(User user) {
        return fileDao.selectCount(user);
    }

    /**
     * 当创建新文件时（文件上传或者是创建文件夹）
     * @param newpath 路径
     * @param name    名称
     * @param page    页
     * @return  前端格式化返回数据
     */
    @Override
    public ResultData mkDir(String newpath, String name, int page, User user) {
        ResultData rd =new ResultData();
        GetIcon getIcon = new GetIcon();


        //获取用户的全部文件列表
        List<UserFile> ufs=fileDao.selectFileList(user);
        //获得？ 列表  将文件列表  路径 页数值传入
        PathAnalysis pa= new PathAnalysis();
        List<UserFile> fms=pa.getNewFloder(ufs, newpath, page);

        UserFile newfileim = new UserFile();
        newfileim.setVfname(name);

        newfileim.setIconsign("#icon-folder");
        newfileim.setFilesize("-");

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = date.format(new Date());
        newfileim.setUptime(format);


        VoToBean voToBean = new VoToBean();
        addVFile(voToBean.fileimToUserFile(newfileim, user.getId(), newpath));


        JSONObject message = new JSONObject();
        JSONArray data =new JSONArray();
        for(int i=0;i<fms.size();i++) {
            UserFile temp = fms.get(i);
            JSONObject tempj = new JSONObject();
            tempj.put("filesize", temp.getFilesize());
            tempj.put("vfname", temp.getVfname());
            tempj.put("uptime", temp.getUptime());
            tempj.put("iconsign", temp.getIconsign());
            data.add(tempj);
        }
        JSONObject msg = new JSONObject();
        msg.put("Catalogue", page);
        msg.put("currentpath", newpath);
        message.put("code", 0);
        message.put("msg", msg);
        message.put("data",data);
        rd.setData(message);
        return rd;
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
