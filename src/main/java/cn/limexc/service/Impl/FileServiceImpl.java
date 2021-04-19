package cn.limexc.service.Impl;

import cn.limexc.dao.FileDao;
import cn.limexc.dao.UserDao;
import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;
import cn.limexc.util.ByteUnitConversion;
import cn.limexc.util.PathAnalysis;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    @Resource
    private FileDao fileDao;
    @Resource
    private UserDao userDao;

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
    public List<UserFile> listUserFileByType(User user, String type) {
        return fileDao.selectFileByType(user,type);
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

        }else if (userFile!=null && userFile.getFid()==null){
            //获取虚拟目录名
            String vpathname = uf.getVfname();
            String[] sourcepath = userFile.getVpath().split("/");
            //更新因为目录名称更改影响到的 路径信息
            for (int i=0;i<ufs.size();i++){
                UserFile temp = ufs.get(i);
                String path = temp.getVpath();
                //判断列表中是否有要修改的文件路径开始的路径
                if (path.startsWith(vpath)){
                    //将匹配的路径分解
                    String[] temps = path.split("/");
                    //获取原始路径的长度，从前开始替换
                    for (int j = 0; j < sourcepath.length; j++) {
                        if (j== sourcepath.length-1){
                            temps[j] = vpathname;
                        }
                    }
                    //将修改的数据重新拼接
                    String tmpath = StringUtils.join(temps, "/");

                    //如果原始路径与当前循环的路径相同
                    if (vpath.equals(temp.getVpath())){
                        //将修改后的路径信息添加到文件夹id的文件上
                        userFile.setVpath(tmpath);
                    }

                    temp.setVpath(tmpath);


                    System.out.println("需要改名的目录或文件"+path+"--->"+userFile.getVpath()+" --> "+vpathname+" 临时赋值 "+tmpath);
                    sum += fileDao.updateVnameAndVpath(temp);
                }

            }
            //最后目录名更新
            userFile.setVfname(vpathname);
            sum += fileDao.updateVnameAndVpath(userFile);

        }

        return sum;
    }

    @Override
    public List<UserFile> selectFiles(Integer id, String key) {
        return fileDao.selectFilesByKey(id,key);
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
        //因为一开始的时候没有考虑文件夹的情况，所以当时分页写的很简单，后来加上文件夹后，分页想搞要改很多东西...
        List<UserFile> userFiles = fileDao.selectFileListLimit(id,page,limit);

        //循环修改filsize的值，数据库中存储原始未转换数据方便对用户空间的控制。
        String tmp;
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
    public Map<String,Object> mkDir(String newpath, String name, int page, User user) {
        //ResultData rd =new ResultData();
        Map<String,Object> map = new HashMap<String ,Object>();
        //获取用户的全部文件列表
        List<UserFile> ufs=fileDao.selectFileList(user);
        //获得？ 列表  将文件列表  路径 页数值传入
        PathAnalysis pa= new PathAnalysis();
        List<UserFile> fms=pa.getNewFloder(ufs, newpath, page);
        //uid,vpath,fid,vfname,uptime
        UserFile newfileim = new UserFile();
        newfileim.setVfname(name);
        newfileim.setVpath(newpath+name);

        newfileim.setUid(user.getId());
        newfileim.setFid(null);
        newfileim.setIconsign("#icon-folder");
        newfileim.setFilesize("");

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = date.format(new Date());
        newfileim.setUptime(format);

        System.out.println(newfileim.toString());

        //VoToBean voToBean = new VoToBean();
        //addVFile(voToBean.fileimToUserFile(newfileim, user.getId(), newpath));
        addVFile(newfileim);

        //JSONObject message = new JSONObject();
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
        map.put("code", 0);
        map.put("msg", msg);
        map.put("data",data);

        return map;
    }






    /**
     * 有问题，因为是前字符串匹配，所以前面相等的都会被涉及到
     * 	 * 如 /文件夹A
     * 	 *    /文件夹B
     * 	 *    /文件夹
     * 	 * 当删除 文件夹 时这 三个都会被删除！！！
     * 初步的修改想法：
     * 		1.通过拆分后的字符串数组进行匹配
     * 		2.通过前匹配后验证后面是否是“/”或后面为空
     * 	*   3.判断后面是否是"/"或 内容是否一致
     *
     */

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

            char[] cpath = path.toCharArray();
            //有问题，后面的不一定是/
            int l = userFile.getVpath().length();

            if (path.startsWith(userFile.getVpath())){
                if (path.equals(userFile.getVpath())||cpath[l]=='/'){
                    System.out.println("要删除的目录"+path);
                    sum += fileDao.deleteUserFile(temp);
                }


            }
        }
        return sum;
    }

    @Override
    public BigInteger sumUserFileSize(User user) {
        //BigDecimal temp;
        //fileDao.sumUserFileSize(user.getId()) 返回String类型
        String size = (fileDao.sumUserFileSize(user.getId()));
        if ("0".equals(size)||size==null) {
            return new BigInteger(String.valueOf(0));
        }else {
            //因为BigInteger使用科学计数法，所有使用new BigDecimal 进行转换
            //temp= new BigDecimal(size);
            return new BigInteger(size);
        }


    }

    /**
     * 使用map保存相关存储空间的信息，包括：
     *      当前用户使用的存储空间--nowStorage
     *      当前用户被分配的存储空间--storage
     *      当前存储空间的占比--percentage
     *      ----以及----
     *      用来判断storage是否为空的--isNull
     *      用来判断是否超过存储空间的--isOut
     * 可以使用一个对象来保存这些信息。
     * @param user  带有id属性的user
     * @return      map
     */
    @Override
    public Map<String, Object> userStorage(User user) {
        Map<String,Object>  map = new HashMap<String,Object>();
        BigDecimal nowStorage = new BigDecimal(sumUserFileSize(user));
        map.put("nowStorage",nowStorage);
        User temuser = userDao.selectUserAllInfoById(user.getId());
        BigDecimal storage= new BigDecimal(temuser.getStorage());
        map.put("storage",storage);
        if (storage.equals(new BigDecimal(0))){
            map.put("isNull",true);
        }else {
            map.put("isNull",false);
        }
        //compareTo方法来比较，小于则返回-1，等于则返回0，大于则返回1
        if (storage.compareTo(nowStorage)<=0){
            map.put("isOut",true);
            map.put("percentage","100%");
        }else {
            map.put("isOut",false);
            //使用BigDecimal进行计算并保留2位小数  以及舍入模式
            BigDecimal resBigIntegers = (nowStorage.divide(storage,2,BigDecimal.ROUND_HALF_UP));

            System.out.println("计算的百分比为：" + resBigIntegers+" 原始的数据为："+nowStorage+"  "+storage);
            String temp = String.valueOf(resBigIntegers).substring(2,4);
            map.put("percentage",temp+"%");
        }
        map.put("ns",new ByteUnitConversion().readableFileSize(Long.parseLong(String.valueOf(nowStorage)))
                +"/"+new ByteUnitConversion().readableFileSize(Long.parseLong(String.valueOf(storage)))
        );
        System.out.println(map.get("ns"));

        return map;
    }
}
