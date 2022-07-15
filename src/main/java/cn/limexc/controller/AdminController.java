package cn.limexc.controller;

import cn.limexc.model.FileModel;
import cn.limexc.model.Group;
import cn.limexc.model.Massage;
import cn.limexc.model.User;
import cn.limexc.service.FileService;
import cn.limexc.service.GroupService;
import cn.limexc.service.MsgService;
import cn.limexc.service.UserService;
import cn.limexc.util.ByteUnitConversion;
import cn.limexc.util.DiskSize;
import cn.limexc.util.MailUtils;
import cn.limexc.util.ResultData;
import cn.limexc.util.SendMailMsg;
import cn.limexc.util.TimeUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    @Resource
    private MsgService msgService;

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
     * 用于转跳 用户请求信息 页面
     * @return othersetpage
     */
    @RequestMapping(value = "/massagepage")
    public  String otherSetting(HttpServletResponse rep,HttpServletRequest req){
        String type = req.getParameter("type");
        rep.setCharacterEncoding("UTF-8");
        req.setAttribute("type", type);
        return "massage";
    }

    /**

     */
    @RequestMapping(value = "/sendmailpage")
    public  String sendmailpage(HttpServletResponse rep,HttpServletRequest req){
        return "sendmail";
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
        String mail = userService.userinfo(Integer.parseInt(uid)).getEmail();
        Boolean isOK = userService.changeUserStatus(Integer.parseInt(uid));

        if (isOK){
            rd.setData("yes");
            new SendMailMsg().mailMsg(mail);
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
        String mail = userService.userinfo(Integer.parseInt(uid)).getEmail();

        if (isOK){
            rd.setData("yes");
            new SendMailMsg().mailMsg(mail);
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
        String mail = userService.userinfo(Integer.parseInt(uid)).getEmail();

        Boolean isOK = userService.changeUserStorage(storage,Integer.parseInt(uid));

        if (isOK){
            rd.setData("yes");
            new SendMailMsg().mailMsg(mail);
        }else {
            rd.setData("no");
        }

        rd.writeToResponse(rep);
    }

    /**
     * 用户请求信息列表
     * @return
     */
    @RequestMapping(value = "/system/msglist")
    @ResponseBody
    public  Map<String,Object> getMsgList(HttpServletRequest request) {
        String type = request.getParameter("type");
        System.out.println(type);
        Map<String, Object> msgData = new HashMap<String, Object>();
        msgData.put("code", 0);
        msgData.put("msg","");
        List<Massage> msgs = msgService.listMsgByType(Integer.parseInt(type));

        JSONArray data =new JSONArray();
        for (Massage msg:msgs) {
            JSONObject jsonMsg = new JSONObject();

            jsonMsg.put("id", msg.getId());
            //通过uid获得用户名
            jsonMsg.put("username", userService.userallinfo(msg.getUid()).getUsername());
            jsonMsg.put("status", msg.getStatus());
            jsonMsg.put("title", msg.getTitle());
            jsonMsg.put("create_time", msg.getCreate_time());
            jsonMsg.put("info", msg.getInfo());
            jsonMsg.put("size", msg.getSize());
            jsonMsg.put("replay", msg.getReply());


            data.add(jsonMsg);
        }


        msgData.put("data",data);
        return msgData;
    }

    /**
     * 上面是工单的列表
     * 下面做工单的功能
     * 需要传入工单的id，用来确定某个工单
     */
    @RequestMapping("/system/set_msg_status")
    @ResponseBody
    public void doMsg(@RequestBody Map jsmp,HttpServletRequest req,HttpServletResponse rep){

        Massage msg = new Massage();
        msg.setId((Integer) jsmp.get("id"));
        msg = msgService.getMsgAdmin(msg.getId());
        msg.setStatus((Integer) jsmp.get("status"));
        msg.setReply((String) jsmp.get("rep"));

        if (msg.getSize()!=null && !msg.getSize().equals("") && msg.getStatus()!=2){
            userService.changeUserStorage(msg.getSize(), msg.getUid());

            String mail = userService.userinfo(msg.getUid()).getEmail();
            new SendMailMsg().mailMsg(mail);
        }

        System.out.printf(msg.toString());
        ResultData rd = new ResultData();
        if (msgService.upMsg(msg)){
            rd.setData("ok");
        }else {
            rd.setData("err");
        }
        rd.writeToResponse(rep);
    }

    @RequestMapping(value = "/system/getSelectEmail",method= RequestMethod.POST)
    @ResponseBody
    public void getSelectEmail(HttpServletResponse response){
        //获取系统中全部用户的用户名和邮箱
        List<User> users = userService.listUser();

        StringBuffer html=new StringBuffer();
        html.append("<option value=\"\">请选择</option>");
        html.append("\n");
        int i= 0;
        //通过循环将数据转换
        for (User user : users){
            if (i==0){
               html.append("<option value=\""+user.getEmail()+"\" selected=\"\">"+user.getUsername() +"</option>");
               html.append("\n");
            }else {
                html.append("<option value=\""+user.getEmail()+"+\">"+user.getUsername() +"</option>");
                html.append("\n");
            }
            i++;
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("contentType", "text/html; charset=utf-8");
            response.getWriter().write(String.valueOf(html));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/system/sendmail")
    public void sendMailAdmin( HttpServletResponse rep,@RequestBody Map jsmp){

        String email = (String) jsmp.get("email_add");
        String title = (String) jsmp.get("title");

        StringBuffer text=new StringBuffer();
        text.append("<h3>用户："+email+" 您好</h3>");
        text.append((String) jsmp.get("info"));
        text.append("<p>此邮件由系统自动发送，请勿回复此邮件。</p></br></br>" +
                "<p style=\"disable:block; left:60px;\">咸闲贤鱼</p></br>"+
                "<p>"+ TimeUtils.getUtils().getForMatTime()+"</p>"
        );
        MailUtils mail = new MailUtils(email,title,text.toString());

        //System.out.printf(msg.toString());
        ResultData rd = new ResultData();

        if (mail.sendMail()){
            rd.setData("ok");
        }else {
            rd.setData("err");
        }
        rd.writeToResponse(rep);
    }


}
