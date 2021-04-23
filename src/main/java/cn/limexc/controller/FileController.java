package cn.limexc.controller;

import cn.limexc.model.FileModel;
import cn.limexc.model.ProFile;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;
import cn.limexc.service.ProFileService;
import cn.limexc.service.UserService;
import cn.limexc.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 主要做文件上传下载吧?
 * 涉及到真实文件操作的。
 *
 */
@Controller
@RequestMapping(value = "/file")
@PropertySource(value = {"classpath:system.properties"})
public class FileController {

    @Resource
    private FileService fileService;
    @Resource
    private UserService userService;
    @Resource
    private ProFileService proFileService;

    private User user;

    private FileModel file;
    private UserFile userFile;

    @Value("${file.path}")
    private String filepath;
    @Value("${file.path.image}")
    private String imageFile;


    //文件上传，能到这里的肯定是经过md5查找后数据库中没有记录的

    @RequestMapping(value = "/upload",method= RequestMethod.POST)
    @ResponseBody
    public FileModel upload(HttpSession session, HttpServletRequest req,@RequestPart("file") MultipartFile multipartFile){
        user= (User) session.getAttribute("user");
        System.out.println(new Date()+"文件来啦");

        if (multipartFile.isEmpty()){
            System.out.println("文件为空");
        }
        String fileMd5 = req.getParameter("md5value");
        String currentpath = req.getParameter("currentpath");
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        //获取文件的大小转换
        String fileSize = Long.toString(multipartFile.getSize());
        System.out.println("文件大小："+fileSize+" 文件名："+fileName);

        //设置属性  等着放到service里面，不是在这里搞的
        GetIcon getIcon = new GetIcon();
        GetFileType fType = new GetFileType();
        file = new FileModel();
        file.setFilesize(fileSize);
        file.setFilename(fileName);
        file.setMd5(fileMd5);
        file.setCreate_time(TimeUtils.getUtils().getForMatTime());
        //设置文件类型
        file.setFiletype(fType.fileType(fileName));
        file.setIconsign(getIcon.Icons(fileName));

        //为了防止有重名的文件，就用时间戳做唯一目录名
        file.setRealpath(filepath+new Date().getTime()+"/"+fileName);


        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("文件的后缀名是："+suffixName);
        
        System.out.println(file.toString());

        //设置文件存储路径
        File upfile = new File(file.getRealpath());
        //检测目录是否存在:使用getParentFile 防止文件名也成为目录 即 只创建到父目录
        if (!upfile.getParentFile().exists()){
            //新建文件夹
            upfile.getParentFile().mkdirs();
        }

        try {
            multipartFile.transferTo(upfile);
            System.out.println("文件写入成功");
            //向数据库写入文件信息
            fileService.addFile(file);
            //查询一下最新的信息
            file = fileService.getFileInfoByMd5(fileMd5);

            userFile = new UserFile();
            userFile.setUid(user.getId());
            userFile.setFid(file.getId());
            userFile.setFilesize(fileSize);
            userFile.setUptime(file.getCreate_time());
            userFile.setVfname(fileName);
            userFile.setIconsign(new GetIcon().Icons(fileName));
            userFile.setVpath(currentpath+fileName);


            fileService.addVFile(userFile);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


    //下载文件,get/post请求均可
    @RequestMapping(value = "/download*")
    public void downFile(HttpServletRequest req, HttpServletResponse rep){
        //用userfileid做标识
        String ufid=req.getParameter("ufid");
        file=fileService.getFileInfoByUFid(ufid);
        if (file==null){
            System.out.println("错误，没有此文件，或用户已将文件删除");
        }else {
            System.out.println("即将下载的文件： "+file.getRealpath()+"  文件重命名为："+file.getFilename());
            File df = new File(file.getRealpath());
            new DownLoadFile().downloadFile(rep,df,file.getFilename());
        }

    }

    //头像上传
    @ResponseBody
    @RequestMapping("/uploadImage")
    public JSON uploadFile(MultipartFile file, HttpServletRequest request) {
        JSONObject json=new JSONObject();

        String filePath = imageFile;//上传到这个文件夹
        File file1 = new File(filePath);
        //判断路径是否存在
        if (!file1.exists()) {
            file1.mkdirs();
        }
        String filename = file.getOriginalFilename().trim();
        String finalFilePath = filePath + filename;
        File desFile = new File(finalFilePath);
        if (desFile.exists()) {
            desFile.delete();
        }
        //用文件名+时间 生成文件url
        String[] shortUrl =new StrMd5Utils().shortUrl(new Date().getTime()+filename);
        //简单一点就第一个了
        proFileService.upProFile(filename,finalFilePath,shortUrl[1]);


        try {
            file.transferTo(desFile);
            json.put("code",0);
            json.put("msg","ok");

            JSONObject data=new JSONObject();
            data.put("src",shortUrl[1]);
            data.put("title","");
            json.put("Data",data);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            json.put("code",1);
        }
        System.out.println(json);
        return json;
    }

    //图片展示,get/post请求均可
    @RequestMapping(value = "/viewimage/{src}")
    public void viewImage(@PathVariable String src,HttpServletRequest req, HttpServletResponse rep){
        //用url做标识
        ProFile proFile = proFileService.getProFile(src);
        if (proFile==null){
            System.out.println("错误，没有此文件，或用户已将文件删除");
        }else {
            //System.out.println("即将下载的文件： "+proFile.getPath());
            File df = new File(proFile.getPath());
            new DownLoadFile().downloadFile(rep,df,proFile.getName());
        }

    }

}
