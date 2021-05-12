package cn.limexc.controller;

import cn.limexc.model.FileModel;
import cn.limexc.model.Group;
import cn.limexc.model.User;
import cn.limexc.service.FileService;
import cn.limexc.service.GroupService;
import cn.limexc.service.UserService;
import cn.limexc.util.ByteUnitConversion;
import cn.limexc.util.DiskSize;
import cn.limexc.util.ResultData;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Resource
    private GroupService groupService;
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
     * 用于转跳 系统全部用户列表页
     * @return userlist
     */
    @RequestMapping(value = "/userlistpage")
    public String userListpage(){
        return "userlist";
    }

    /**
     * 用于转跳 系统全部文件管理页面
     * @return filelist
     */
    @RequestMapping(value = "/filelistpage")
    public String fileListPage(){
        return "filelist";
    }

    /**
     * 用于转跳 系统其他设置 页面
     * @return othersetpage
     */
    @RequestMapping(value = "/othersetpage")
    public  String otherSetting(){
        return "otherset";
    }

    /**
     * 系统信息展示数据
     * 用户数、文件数、
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

    /**
     * 系统所有文件的展示。
     * @return
     */
    @RequestMapping(value = "/system/filelist")
    @ResponseBody
    public Map<String,Object> getFileList(){
        Map<String,Object> fileData = new HashMap<String,Object>();
        fileData.put("code", 0);
        fileData.put("msg","");
        List<FileModel> fileModels = fileService.listAllFile();
        for (FileModel fl:fileModels) {
            fl.setFilesize(new ByteUnitConversion().readableFileSize(Long.parseLong(fl.getFilesize())));
        }
        fileData.put("data",fileModels);

        return fileData;
    }

    /**
     * 用户列表
     * @return
     */
    @RequestMapping(value = "/system/userlist")
    @ResponseBody
    public  Map<String,Object> getUserList() {
        Map<String, Object> userData = new HashMap<String, Object>();
        userData.put("code", 0);
        userData.put("msg","");
        List<User> users = userService.listUser();
        Group group = null;

        JSONArray data =new JSONArray();
        for (User user:users) {
            JSONObject jsonuser = new JSONObject();
            jsonuser.put("id",user.getId());
            jsonuser.put("username",user.getUsername());
            //按老师说的，添加一个用户已经使用了多少空间的数据展示。
            //拿id挨个查？
            Map<String,Object> storageInfoMap = fileService.userStorage(user);
            jsonuser.put("now_storage",storageInfoMap.get("ns"));

            jsonuser.put("storage",new ByteUnitConversion().readableFileSize(Long.parseLong(user.getStorage())));
            jsonuser.put("email",user.getEmail());
            if (user.getStatus().equals(0)){
                jsonuser.put("status",true);
            }else {
                jsonuser.put("status",false);
            }
            jsonuser.put("create_at",user.getCreate_at());
            group = groupService.getUserGroup(user.getId());
            if (group.getId().equals(1)){
                jsonuser.put("group",true);
            }else {
                jsonuser.put("group",false);
            }

            data.add(jsonuser);
        }


        userData.put("data",data);
        return userData;
    }

    /**
     * 改变传入的数据的 status 属性
     */
    @RequestMapping("/system/changeStatus")
    public void changeStatus(HttpServletResponse rep, HttpServletRequest req){
        ResultData rd = new ResultData();
        String uid=req.getParameter("userid");
        //String status=req.getParameter("status");

        Boolean isOK = userService.changeUserStatus(Integer.parseInt(uid));

        if (isOK){
            rd.setData("yes");
        }else {
            rd.setData("no");
        }

        rd.writeToResponse(rep);
    }


    /**
     * 改变传入的数据的 group 属性
     */
    @RequestMapping("/system/changeGroup")
    public void changeGroup(HttpServletResponse rep, HttpServletRequest req){
        ResultData rd = new ResultData();
        String uid=req.getParameter("userid");
        //String status=req.getParameter("status");

        Boolean isOK = groupService.changeUserGroup(Integer.parseInt(uid));

        if (isOK){
            rd.setData("yes");
        }else {
            rd.setData("no");
        }

        rd.writeToResponse(rep);
    }

    /**
     * 改变用户空间容量
     * @param rep
     * @param req
     */
    @RequestMapping("/system/changeStorage")
    public void  changeStorage(HttpServletResponse rep, HttpServletRequest req){
        ResultData rd = new ResultData();
        String uid=req.getParameter("userid");
        String storage=req.getParameter("storage");

        Boolean isOK = userService.changeUserStorage(storage,Integer.parseInt(uid));

        if (isOK){
            rd.setData("yes");
        }else {
            rd.setData("no");
        }

        rd.writeToResponse(rep);
    }

}
