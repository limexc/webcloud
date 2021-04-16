package cn.limexc.controller;

import cn.limexc.model.Group;
import cn.limexc.model.User;
import cn.limexc.service.FileService;
import cn.limexc.service.GroupService;
import cn.limexc.service.UserService;
import cn.limexc.util.MailUtils;
import cn.limexc.util.StrMd5Utils;
import cn.limexc.util.TimeUtils;
import cn.limexc.util.VeCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主要要做用户的验证登录注册找回密码等还未登录，以及登录操作【看看以后把登录拿出来】。
 * 用户退出暂时例外，因为还没弄好。
 * index      index页面转跳用，我也不知道为什么写的这东西了。
 * userlogin  用户登录
 * register   用户注册
 * findPassword  密码找回
 * sendMail      邮件发送
 * logOut        用户退出
 */


@Controller
@RequestMapping(value = "/")
public class AccountController {

    @Resource
    private UserService userService;
    @Resource
    private GroupService groupService;
    @Resource
    private FileService fileService;



    //处理直接访问的根请求
    @RequestMapping(value = "")
    public String index(){
        return "forward:/login.jsp";
    }

    @RequestMapping(value = "/system/login")
    public String userlogin(HttpServletRequest request, Model model, HttpSession session){
        String email;
        String password;
        User user = null;
        //总是傻了吧唧的，别忘了，暂存。
        //req.setCharacterEncoding("UTF-8");
        //resp.setContentType("text/html;charset=UTF-8");

        email = request.getParameter("account");
        password = request.getParameter("password");

        //正则表达式判断是否为邮箱  --- 可以抽取放在util中
        String mailcheck = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        // 邮箱验证规则
        // 编译正则表达式
        Pattern pattern = Pattern.compile(mailcheck);
        Matcher matcher = pattern.matcher(email);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();


        if (rs && password!=null && password!=""){

            System.out.println("邮箱："+email+" 密码："+password+" 邮箱格式："+rs);
            //将查询到的数据放到user中
            user = userService.login(email,password);
            //获取用户组的信息
            Group group = groupService.getUserGroup(user.getId());

            if (user!=null){
                System.out.println("用户信息："+user.toString());
                System.out.println(StrMd5Utils.MD5(user.getPassword()));
                //mv.setViewName("redirect:/index.jsp");
                //httpSession.setAttribute("id","");

                //将用户登陆数据保存到session中
                session.setAttribute("user",user);
                session.setAttribute("group",group);
                //session.setAttribute("status",user.getStatus());
                //存储用户的权限？没有实体类对应啊？
                session.setAttribute("power",group.getPower());

                //获取用户的容量信息
                /**
                 * 使用map保存相关存储空间的信息，包括：
                 *      当前用户使用的存储空间--nowStorage
                 *      当前用户被分配的存储空间--storage
                 *      当前存储空间的占比--percentage
                 *      ----以及----
                 *      用来判断storage是否为空的--isNull
                 *      用来判断是否超过存储空间的--isOut
                 */
                Map<String,Object> storageInfoMap = fileService.userStorage(user);
                session.setAttribute("percentage",storageInfoMap.get("percentage"));
                session.setAttribute("isout",storageInfoMap.get("isOut"));
                //在前端判断isout如果超出的禁用上传功能，在后端--用户登陆系统的时候需要判断，并禁止上传。

                for (Map.Entry<String, Object> map : storageInfoMap.entrySet()) {

                    System.out.println("Key = " + map.getKey() + ", Value = " + map.getValue());

                }
                //END  存储空间





                return "redirect:/user/main";
            }
            return "forward:/login.jsp";
        }else {
            //当时为啥要加个model？等后期检查一下
            model.addAttribute("msg","登录失败，请检查用户名密码");
            return "forward:/login.jsp";
        }

    }

    /**
     * 转跳注册页
     * @return 注册页地址
     */
    @RequestMapping(value = "/register")
    public String regPage(){
        return "forward:/register.jsp";
    }

    /**
     * 这个方法写的有问题，在regUEPR中校验过，通过校验后在此方法内没有再一次校验
     * 所以如果通过一次校验后，长时间没有提交（没有再一次触发相关Ajax），导致验证码过期也是可以通过校验。
     * ---在点击 提交 按钮时通过js再全部判断一次。虽然前端传来的数据都是不可信的，但先这样吧。有时间再进行修改。
     *
     * @param req 请求
     * @param rep 响应
     */
    @RequestMapping(value = "/system/reg/updata")
    @ResponseBody
    public void register(HttpServletRequest req,HttpServletResponse rep) {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String passwd=req.getParameter("passwd");

        System.out.println("邮箱：" + email + " 密码：" + passwd);
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwd);
        user.setUsername(username);

        Integer regnum = userService.regUser(user);
        try {
            if (regnum == 1) {

                rep.getWriter().print("true");

            } else {

                rep.getWriter().print("false");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 用于校验用户在填写表单时的数据
     * @param req 传入的数据
     * @param rep 响应的数据
     */

    @RequestMapping(value = "/system/reg/service")
    @ResponseBody
    public void regUEPR(HttpServletRequest req,HttpServletResponse rep){

        Enumeration<String> key = req.getParameterNames();
        while(key.hasMoreElements()) {

            String parameterName = key.nextElement();
            System.out.println(parameterName);
            //判断传入的参数是邮箱还是用户名
            if ("loginName".equals(parameterName)){
                try {
                    rep.getWriter().print(userService.haveUserByName(req.getParameter("loginName")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if ("email".equals(parameterName)){
                try {
                    rep.getWriter().print(userService.haveUserByEmail(req.getParameter("email")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if ("regcode".equals(parameterName)){
                String regcode_sys = new VeCodeUtils().getVeCode(req.getParameter("email"));
                String regcode_user = req.getParameter("regcode");
                System.out.println(regcode_sys+"  "+regcode_user);
                try {
                    if (regcode_user.equals(regcode_sys)){
                        rep.getWriter().print("true");
                    }else {
                        rep.getWriter().print("false");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            break;

        }



    }





    @RequestMapping(value = "/resetpwd")
    public ModelAndView findPasswdPage(){
        ModelAndView mv = new ModelAndView("forward:/findpasswd.jsp");
        return mv;
    }



    /**
     * 还没测试，准备睡觉了功能还没搞，大体就这样发送
     * 明天再改改，不应该这样写
     * @param req
     */
    @RequestMapping(value = "/system/sendmail",method = RequestMethod.POST)
    @ResponseBody
    public void sendMail(HttpServletRequest req){

        String email = req.getParameter("email");

        StringBuffer text=new StringBuffer("<h1>来自CloudWeb的验证码</h1>");
        text.append("<h3>用户："+email+" 您好</h3>");
        text.append("本次的验证码为: <h4>"+ new VeCodeUtils().getVeCode(email) + "</h4>");
        text.append("<p>如果您没有在<a href=\"#\">小贤网盘</a>注册账号或更改您的账号信息，请忽略此电子邮件。</p></br>" +
                "<h5>验证码有效时间为10分钟</h5>");
        text.append("<p>不要把链接给任何人。</p></br>" +
                "<p>此邮件由系统自动发送，请勿回复此邮件。</p></br></br>" +
                "<p style=\"disable:block; left:60px;\">咸闲贤鱼</p></br>"+
                "<p>"+TimeUtils.getUtils().getForMatTime()+"</p>"
        );
        MailUtils mail = new MailUtils(email,"验证码",text.toString());
        mail.sendMail();
    }

    /**
     * 退出系统，注销登陆
     * @param session 用户session
     * @return 转跳登陆界面
     */
    @RequestMapping(value = "/system/logout")
    public String logOut(HttpSession session){
        //清除session
        session.invalidate();
        return "redirect:/login.jsp";
    }

}
