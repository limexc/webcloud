package cn.limexc.controller;

import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;
import cn.limexc.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zhiyuanxzy@gmail.com
 * @version 1
 * @Description 用于文件分享功能的控制器
 * @create 2021-04-01 21:53
 * @since jdk1.8.0
 */
@Controller
@RequestMapping(value = "/share")
public class ShareFileController {

    @Resource
    private FileService fileService;
    private User user;
    private FileModel fileModel;
    private UserFile userFile;

    /**
     * 用户设置分享页面，可以用Ajax直接在弹框中显示分享连接，就不用再写个页面了。也就没有那么多要搞的参数了。哈哈哈哈
     *
     */
    @RequestMapping(value = "/getshareurl")
    @ResponseBody
    public Map<String,Object> shareSet(HttpServletRequest req, HttpSession session){
        Map<String,Object> map = new HashMap<String,Object>();
        //关于链接的生成，将uid和ufid拼接【只能生成一个链接】，然后返回短格式信息，并将这个信息写入数据库
        //传入的数据需要先进行验证【通过查询数据库等方式】，然后再进行编码，编码完成后进行数据库查找【防止出现链接指向多个文件】判断是否重复。
        //获取传入的ufid -- 也需要判断格式等问题，以后再说
        Integer ufid = Integer.parseInt(req.getParameter("ufid"));

        user = (User) session.getAttribute("user");
        Integer uid = user.getId();
        //用传入的ufid验证一下是否是当前用户的文件，以及是不是随便写了一个数。防止用户瞎写共享了其他人的文件。
        userFile = fileService.getUFInfoByUFid(ufid);
        if (userFile!=null&&uid==userFile.getUid()){
            System.out.println("正在生成分享链接"+ufid);

            map.put("url","aaaaa");

            return map;
        }else {
            //瞎写的，给返回个警告信息，可以改改违规多少次就封了他，让他瞎写。

            System.out.println("错误");
            map.put("url","null");
            return map;
        }





    }

    /**
     * 用户获取分享页面,搞简单点，和那个  蓝奏云  的差不多就行，简单
     * @return
     */
    @RequestMapping(value = "/file/{url}")
    public String shareGet(@PathVariable String url){
        //在用户访问时使用shareGet来获取传递的参数,Get请求。通过查询数据库将文件的信息返回并写入到页面中。
        //获取URL参数，用于确定唯一的文件分享地址，这个url用
        System.out.println("用户要访问的"+url);
        //加判断，从数据库获取信息然后显示出来供用户下载。
        //别人分享的文件可以加入到自己列表中的功能后面看看时间充裕就添加上。


        return "forward:/sharefile.jsp";
    }


}
