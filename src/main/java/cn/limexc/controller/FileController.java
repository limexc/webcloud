package cn.limexc.controller;

import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.service.FileService;
import cn.limexc.service.UserService;
import cn.limexc.util.ByteUnitConversion;
import cn.limexc.util.DownLoadFile;
import com.sun.deploy.net.HttpResponse;
import com.sun.org.apache.xml.internal.security.keys.storage.implementations.CertsInFilesystemDirectoryResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpRequest;
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
import java.util.Map;

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

    private User user;

    private FileModel file;

    @Value("${file.path}")
    private String filepath;

    //文件上传，能到这里的肯定是经过md5查找后数据库中没有记录的

    @RequestMapping(value = "/upload",method= RequestMethod.POST)
    @ResponseBody
    public FileModel upload(HttpSession session, HttpServletRequest req,@RequestPart("file") MultipartFile multipartFile){
        System.out.println(new Date()+"文件来啦");

        if (multipartFile.isEmpty()){
            System.out.println("文件为空");
        }
        String fileMd5 = req.getParameter("md5value");
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        //获取文件的大小并进行单位的转换计算
        String fileSize = new ByteUnitConversion().readableFileSize(multipartFile.getSize());
        System.out.println("文件大小："+fileSize+" 文件名："+fileName);

        //设置属性
        file = new FileModel();
        file.setFilesize(fileSize);
        file.setFilename(fileName);
        file.setMd5(fileMd5);
        file.setCreate_time(new Date());

        //为了防止有重名的文件，就用时间戳做唯一目录名
        file.setRealpath(filepath+file.getCreate_time().getTime()+"/"+fileName);


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

}
