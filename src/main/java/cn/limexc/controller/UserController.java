package cn.limexc.controller;


import cn.limexc.model.Group;
import cn.limexc.model.User;
import cn.limexc.service.FileService;
import cn.limexc.service.GroupService;
import cn.limexc.service.UserService;
import cn.limexc.util.ResultData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 主要做登录后用户信息的修改查询等操作。
 *
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private GroupService groupService;
    @Resource
    private FileService fileService;

    @RequestMapping(value = "/listUser")
    @ResponseBody
    public List<User> listUser(){
        List<User> users = userService.listUser();
        return users;
    }

    @RequestMapping(value = "/setting")
    public String userset(){
        System.out.println(new Date()+"访问到了");
        return "setting";
    }


    @RequestMapping(value = "/main")
    public String index(HttpSession session){
        return "forward:/index.jsp";
    }

    @RequestMapping(value = "/repwdpage")
    public String resetpwdpage(){
        return "resetpwd";
    }

    @RequestMapping(value = "/repwd",method = RequestMethod.POST)
    public void resetpwd(HttpSession session, HttpServletRequest req,HttpServletResponse rep){
        User user = (User) session.getAttribute("user");
        String old_passwd = req.getParameter("old_pass");
        String pass1 = req.getParameter("pass1");
        String pass2 = req.getParameter("pass2");
        System.out.println("获取到的密码信息："+old_passwd+" "+pass1);
        ResultData rd = new ResultData();
        Integer isOk = 0;
        //先判断两次新密码是否一致
        if (pass1.equals(pass2)){
            //通过uid查询密码与前端传来的数据比较一致进行下一步或返回错误信息
            isOk = userService.resetpasswd(user.getId(), old_passwd,pass1);
        }
        //不等于0表示密码修改成功
        if (isOk==1){
            //向前端回传一下
            rd.setData("yes");
            rd.writeToResponse(rep);
        }else {
            //回传错误消息
            rd.setData("no");
            rd.writeToResponse(rep);
        }
    }

    //判定用户类别进行页面的设置
    @RequestMapping(value = "/menu_setting",method= RequestMethod.POST)
    @ResponseBody
    public void usertype(HttpSession session, HttpServletResponse response){
        User user = (User) session.getAttribute("user");
        //将完整的用户信息重新存入session中
        user=userService.userallinfo(user.getId());
        Group group = (Group) session.getAttribute("group");
        //更新一下user的信息
        session.setAttribute("user",user);
        StringBuffer html=new StringBuffer();
        html.append("<dd><a href=\"/CloudWeb/user/setting\" target=\"file_info_body\">基本资料</a></dd>");
        html.append("<dd><a href=\"/CloudWeb/user/repwdpage\" target=\"file_info_body\">安全设置</a></dd>");
        System.out.println("来加载菜单等信息了");
        //更新一下存储空间信息
        Map<String,Object> storageInfoMap = fileService.userStorage(user);
        session.setAttribute("percentage",storageInfoMap.get("percentage"));
        session.setAttribute("isout",storageInfoMap.get("isOut"));
        session.setAttribute("ns",storageInfoMap.get("ns"));

        /**
         * 想法：
         * 通过用户查询数据库获得用户所属的用户组，通过组ID获取组权限，判断组的权限来回传相对应的信息。
         * power 0 为管理员组
         *
         */
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("contentType", "text/html; charset=utf-8");
            if ((group).getPower()==0) {
                System.out.println("管理员加载的");
                html.append("<dd><a href=\"/CloudWeb/user/admin\">系统设置</a></dd>");
                response.getWriter().write(String.valueOf(html));
            }else {
                System.out.println("用户加载的");
                response.getWriter().write(String.valueOf(html));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/admin")
    public String getadmin(HttpSession session){
        Group group = (Group) session.getAttribute("group");
        if (group.getPower()==0){
            return "admin";
        }else {
            return "forward:/user/main";
        }

    }

}
