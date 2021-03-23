package cn.limexc.controller;

import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
