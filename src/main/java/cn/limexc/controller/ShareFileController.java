package cn.limexc.controller;

import cn.limexc.model.FileModel;
import cn.limexc.model.ShareFile;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;
import cn.limexc.service.ShareService;
import cn.limexc.service.UserService;
import cn.limexc.util.ByteUnitConversion;
import cn.limexc.util.DownLoadFile;
import cn.limexc.util.ResultData;
import cn.limexc.util.StrMd5Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
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
    @Resource
    private ShareService shareService;
    @Resource
    private UserService userService;



    /**
     * 用户设置分享页面，可以用Ajax直接在弹框中显示分享连接，就不用再写个页面了。也就没有那么多要搞的参数了。哈哈哈哈
     *
     */
    @RequestMapping(value = "/getshareurl")
    @ResponseBody
    public Map<String,Object> shareSet(HttpServletRequest req, HttpSession session){
        User user;
        UserFile userFile;
        Map<String,Object> map = new HashMap<String,Object>();
        //关于链接的生成，将uid和ufid拼接【不拼接，仅用ufid也可，设想一个用户ufid只生成一个链接，会更简单】，然后返回短格式信息，并将这个信息写入数据库
        //传入的数据需要先进行验证【通过查询数据库等方式】，然后再进行编码，编码完成后进行数据库查找【防止出现链接指向多个文件】判断是否重复。
        //获取传入的ufid -- 也需要判断格式等问题，以后再说
        Integer ufid = Integer.parseInt(req.getParameter("ufid"));
        user = (User) session.getAttribute("user");
        Integer uid = user.getId();
        //用传入的ufid验证一下是否是当前用户的文件，以及是不是随便写了一个数。防止用户瞎写共享了其他人的文件。
        userFile = fileService.getUFInfoByUFid(ufid);

        if (userFile!=null&&uid==userFile.getUid()){
            //是不是也要判断一下是否之前为这个生成过分享链接，防止重复生成。


            System.out.println("正在为"+ufid+"文件生成分享链接");
            String str = req.getParameter("ufid")+uid;
            String[] shortUrl =new StrMd5Utils().shortUrl(str);

            //如果之前生成过，直接返回生成的数据。


            ShareFile shareFile = new ShareFile();
            shareFile.setFid(userFile.getFid());
            shareFile.setUfid(userFile.getId());
            shareFile.setUid(uid);



            // 通过shortUrl中的来查找数据库，判断否有相同的url,若不同文件生成了相同的，那么进入下一次比较。
            if (shareService.getUserShareByUFid(userFile.getId())!=null){
                System.out.println("警告！文件"+ufid+"已经共享过了");
                map.put("url","err2");
                //
                map.put("tips",shareService.getUserShareByUFid(userFile.getId()).getUrl());
                return map;
            }

            for (String tmp:shortUrl){
                System.out.println(tmp);

                if (shareService.getUserShareByUrl(tmp)==null){

                    shareFile.setUrl(tmp);
                    shareService.createShare(shareFile);


                    map.put("url","ok");
                    map.put("tips",tmp);
                    return map;

                }

            }


        }else {
            //瞎写的，给返回个警告信息，可以改改违规多少次就封了他，让他瞎写【err1错误】。

            System.out.println("错误");
            map.put("url","err1");
            return map;
        }

        map.put("url","err3");
        return map;


    }

    /**
     * 用户获取分享页面,搞简单点，和那个  蓝奏云  的差不多就行，简单
     * https://img.limexc.cn/images/20210405142005.png
     * 要注意配置Interceptor防止拦截未登录用户访问该URL
     * @return
     */
    @RequestMapping(value = "/file/{url}")
    public ModelAndView shareGet(@PathVariable String url, HttpServletResponse rep){
        ModelAndView  mv = new ModelAndView("forward:/sharefile.jsp");
        Map<String,Object> map = new HashMap<String,Object>();
        //在用户访问时使用shareGet来获取传递的参数,Get请求。通过查询数据库将文件的信息返回并写入到页面中。
        //获取URL参数，用于确定唯一的文件分享地址，这个url用
        System.out.println("用户要访问的"+url);
        //加判断，从数据库获取信息然后显示出来供用户下载。
        //别人分享的文件可以加入到自己列表中的功能后面看看时间充裕就添加上。
        ShareFile shareFile = shareService.getUserShareByUrl(url);
        if (shareFile==null){
            System.out.println("错误！url地址错误！");

            //前端获取信息加个提示
            map.put("status","404");

            mv.addAllObjects(map);
            return mv;
        }
        //获取分享的用户信息添加
        UserFile uf = fileService.getUFInfoByUFid(shareFile.getUfid());
        User us = new  User();
        us= userService.userinfo(shareFile.getUid());
        System.out.println(uf.toString());
        System.out.println(us.toString());
        map.put("filename",uf.getVfname());
        map.put("uptime",uf.getUptime());
        map.put("username",us.getUsername());
        map.put("filesize",new ByteUnitConversion().readableFileSize(Long.parseLong(uf.getFilesize())));
        map.put("filetext",shareFile.getOther());
        map.put("fileurl",url);
        mv.addAllObjects(map);

        return mv;
    }

    //下载分享的文件
    @RequestMapping(value = "/file/download/{url}")
    public void downFile(@PathVariable String url,HttpServletRequest req, HttpServletResponse rep){
        FileModel fileModel = null;
        //通过url来获取文件的信息
        ShareFile sf = shareService.getUserShareByUrl(url);
        if (sf!=null){
            fileModel=fileService.getFileInfoByUFid(String.valueOf(sf.getUfid()));
        }
        if (fileModel==null){
            System.out.println("错误，没有此文件，或用户已将文件删除");
        }else {
            System.out.println("即将下载的文件： "+fileModel.getRealpath()+"  文件重命名为："+fileModel.getFilename());
            File df = new File(fileModel.getRealpath());
            new DownLoadFile().downloadFile(rep,df,fileModel.getFilename());
        }

    }

    /**
     *  用作请求页面的返回
     * @return  共享文件列表页面
     */
    @RequestMapping(value = "/listpage")
    public String sharelistpage(){
        return "sharelist";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String,Object> shareList(HttpSession session){
        Map<String,Object> map = new HashMap<String,Object>();
        User user  = (User) session.getAttribute("user");
        List<UserFile> ufs = shareService.getShareUFList(user.getId());
        for (UserFile uf:ufs) {
            uf.setFilesize(new ByteUnitConversion().readableFileSize(Long.parseLong(uf.getFilesize())));
        }
        map.put("code", 0);
        map.put("msg","");
        map.put("data",ufs);
        return map;
    }

    /**
     * 取消删除分享
     */
    @RequestMapping(value = "/delShare")
    public void delShare(HttpSession session,HttpServletRequest req,HttpServletResponse rep){
        User user = (User) session.getAttribute("user");
        String str_ufid = req.getParameter("ufid");
        //通过uid和ufid找到该分享的文件 ？ 一个文件多次分享咋搞？
        Integer uid = user.getId();
        Integer ufid = Integer.parseInt(str_ufid);
        Integer col = 0;
        col = shareService.deleteSF(uid,ufid);
        ResultData rd= new ResultData();
        if (col!=0){
            rd.setData("ok");
        }else {
            rd.setData("no");
        }
        rd.writeToResponse(rep);
    }

}
