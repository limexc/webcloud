package cn.limexc.controller;


import cn.limexc.model.Group;
import cn.limexc.model.User;
import cn.limexc.service.GroupService;
import cn.limexc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
        html.append("<dd><a href=\"/CloudWeb/user/setting\">基本资料</a></dd>\n" +
                    "<dd><a href=\"\">安全设置</a></dd>\n");
        System.out.println("来加载菜单等信息了");
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
                html.append("<dd><a href=\"" +
                        "/CloudWeb/user/admin" +
                        "\">系统设置</a></dd>");
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
