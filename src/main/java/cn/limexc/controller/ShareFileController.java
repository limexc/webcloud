package cn.limexc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhiyuanxzy@gmail.com
 * @version 1
 * @Description
 * @create 2021-04-01 21:53
 * @since jdk1.8.0
 */
@Controller
@RequestMapping(value = "/share")
public class ShareFileController {



    /**
     * 用户设置分享页面，可以用Ajax直接在弹框中显示分享连接，就不用再写个页面了。也就没有那么多要搞的参数了。哈哈哈哈
     * @return
     */
    public String shareSet(){


        return null;
    }

    /**
     * 用户获取分享页面,搞简单点，和那个  蓝奏云  的差不多就行，简单
     * @return
     */
    @RequestMapping(value = "/file")
    public String shareGet(HttpServletRequest req){
        //获取URL参数，用于确定唯一的文件分享地址，这个url用
        String url = req.getParameter("url");



        return "forward:/sharefile.jsp";
    }


}
