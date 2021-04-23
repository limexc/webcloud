package cn.limexc.controller;

import cn.limexc.model.User;
import cn.limexc.service.FileService;
import cn.limexc.service.UserService;
import cn.limexc.util.ByteUnitConversion;
import cn.limexc.util.DiskSize;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-04-22 9:51
 * @since jdk1.8.0
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Resource
    private UserService userService;
    @Resource
    private FileService fileService;
    @Value("${file.path}")
    private String filePath;

    /**
     * 用于转跳系统信息页
     * @return 系统信息页地址
     */
    @RequestMapping(value = "/sysinfopage")
    public String systemInfoPage(){
        System.out.println("admin sys来啦");
        return "sysinfo";
    }

    /**
     * 系统信息展示数据
     */
    @RequestMapping(value = "/system/info")
    @ResponseBody
    public Map<String ,Object> getSysInfo(){
        Map<String ,Object> map = new HashMap<String ,Object>();

        map.put("user_row",userService.getUserRow());
        map.put("file_row",fileService.getFileRow());
        map.put("now_size",fileService.getAllFileSize());
        String free_size = new DiskSize().getUsableSpace(filePath);
        map.put("free_size",free_size);
        return  map;
    }



    /**
     * 系统新注册用户展示数据 数据库 按日期降序 取5条
     */
    @RequestMapping(value = "/system/newuser")
    @ResponseBody
    public Map<String ,Object> getSysNewUser(){
        //要返回的json格式数据
        Map<String ,Object> map = new HashMap<String ,Object>();
        //封装用户信息的json格式数据
        //Map<String ,Object> data = new HashMap<String ,Object>();
        //要求的返回格式
        map.put("code", 0);
        map.put("msg","");
        List<User> users = userService.getNewUser("5");
        for (User user:users) {
            user.setStorage(new ByteUnitConversion().readableFileSize(Long.parseLong(user.getStorage())));
        }
        map.put("data",users);

        return map;
    }


}
