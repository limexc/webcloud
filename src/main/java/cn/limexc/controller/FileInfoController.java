package cn.limexc.controller;

import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主要做文件信息的增删改查
 *
 */


@Controller
@RequestMapping("/info")
public class FileInfoController {

    @Resource
    private FileService fileService;

    private User user;
    private List<UserFile> userFile;
    private FileModel file;

    @RequestMapping(value = "/userfile*")
    @ResponseBody
    public Map<String, Object> userFile(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        user= (User) session.getAttribute("user");

        //获取分页的参数
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        int count = fileService.UserFileCount(user);
        int pa =Integer.parseInt(page);
        pa=pa-1;
        page = String.valueOf(pa);

        System.out.println("用户ID: "+user.getId()+" 分页参数:"+page+" "+limit+" 数据行数："+count);

        userFile = fileService.listUserFile(user.getId(),page,limit);

        System.out.println("已经执行sql了");
        System.out.println(userFile.toString());

        Map<String,Object> tableData =new HashMap<String,Object>();

        tableData.put("code", 0);
        tableData.put("msg", "");
        //将全部数据的条数作为count传给前台（一共多少条）
        tableData.put("count",count);
        //将分页后的数据返回（每页要显示的数据）
        tableData.put("data", userFile);
        //返回给前端
        return tableData;


    }
    //判断文件是否存在，若存在数据写入数据库

    @RequestMapping(value = "/getmd5",method= RequestMethod.POST)
    @ResponseBody
    public FileModel getMd5(HttpSession session, HttpServletRequest req, @RequestBody Map<String, String> map){
        //HttpRequest req, HttpResponse rep, HttpSession session
        user = (User) session.getAttribute("user");
        System.out.println("用户："+user.getId()+"当前http请求方式为:"+req.getMethod());
        String filesize =map.get("filesize");
        String filemd5 = map.get("md5value");
        System.out.println(filemd5+"  "+filesize);

        //查询数据库，并将结果放入file
        file = fileService.getFileInfoByMd5(filemd5);

        if (file!=null){
            //数据存在
        }

        return file;


    }

}
