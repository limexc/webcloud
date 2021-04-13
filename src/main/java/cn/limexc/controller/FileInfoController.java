package cn.limexc.controller;

import cn.limexc.model.FileModel;
import cn.limexc.model.User;
import cn.limexc.model.UserFile;
import cn.limexc.service.FileService;
import cn.limexc.util.ByteUnitConversion;
import cn.limexc.util.PathAnalysis;
import cn.limexc.util.ResultData;
import cn.limexc.util.TimeUtils;
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

    private List<UserFile> userFile;
    private FileModel file;

    @RequestMapping(value = "/userfile*")
    @ResponseBody
    public Map<String, Object> userFile(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String method = request.getParameter("method");
        User user = (User) session.getAttribute("user");
        //返回到前端的数据
        Map<String, Object> tableData = new HashMap<String, Object>();
        tableData.put("code", 0);

        if ("getSuperior".equals(method)) {
            //获得上一级的文件
            System.out.println("上一级");

            String currentpath = request.getParameter("currentpath");
            String Catalogue = request.getParameter("Catalogue");
            String newpath = "/";
            String[] paths = currentpath.split("/");
            for (int i = 0; i < paths.length - 1; i++) {
                if (paths[i] != null && paths[i].trim().length() != 0)
                    newpath += paths[i] + "/";
            }

            int page = Integer.parseInt(Catalogue);
            if (page > 0) {
                PathAnalysis pa = new PathAnalysis();
                List<UserFile> ufs = fileService.listUserFile(user);
                List<UserFile> fms = pa.SuperiorCatalogue(ufs, newpath, page);

                JSONArray data = new JSONArray();
                for (int i = 0; i < fms.size(); i++) {
                    UserFile temp = fms.get(i);
                    JSONObject tempj = new JSONObject();
                    tempj.put("id",temp.getId());
                    tempj.put("filesize", temp.getFilesize());
                    tempj.put("vfname", temp.getVfname());
                    tempj.put("uptime", temp.getUptime());
                    tempj.put("iconsign", temp.getIconsign());
                    data.add(tempj);
                }

                System.out.println(newpath);
                JSONObject msg = new JSONObject();
                msg.put("Catalogue", page - 1);
                msg.put("currentpath", newpath);



                tableData.put("msg", msg);
                tableData.put("data", data);


                System.out.println("上级目录更新完毕！");
                return tableData;

            }
        } else if ("index".equals(method)) {

            PathAnalysis pa = new PathAnalysis();
            List<UserFile> ufs = fileService.listUserFile(user);
            List<UserFile> ufs1 = pa.getIndexPath(ufs);

            JSONArray data = new JSONArray();

            for (int i = 0; i < ufs1.size(); i++) {
                UserFile temp = ufs1.get(i);
                JSONObject tempj = new JSONObject();
                tempj.put("id",temp.getId());
                tempj.put("filesize", temp.getFilesize());
                tempj.put("vfname", temp.getVfname());
                tempj.put("uptime", temp.getUptime());
                tempj.put("iconsign", temp.getIconsign());
                data.add(tempj);
            }

            JSONObject msg = new JSONObject();
            msg.put("Catalogue", 0);
            msg.put("currentpath", "/");

            tableData.put("msg", msg);
            tableData.put("data", data);

            System.out.println("ok  "+tableData);

            return tableData;
        }else if ("getSub".equals(method)){
            System.out.println("下一级");

            String filename = request.getParameter("filename");
            String currentpath = request.getParameter("currentpath");
            String Catalogue = request.getParameter("Catalogue");
            int page = Integer.parseInt(Catalogue);
            String newpath="";
            System.out.println(currentpath);
            if(currentpath!=null) {
                newpath= currentpath+filename+"/";
            }else {
                newpath="/"+filename+"/";
            }
            PathAnalysis pa= new PathAnalysis();

            List<UserFile> ufs = fileService.listUserFile(user);
            List<UserFile> fms=pa.getSubdirectories(ufs, newpath, page);

            JSONArray data =new JSONArray();
            for(int i=0;i<fms.size();i++) {
                UserFile temp = fms.get(i);
                JSONObject tempj = new JSONObject();
                tempj.put("id",temp.getId());
                tempj.put("filesize", temp.getFilesize());
                tempj.put("vfname", temp.getVfname());
                tempj.put("uptime", temp.getUptime());
                tempj.put("iconsign", temp.getIconsign());
                data.add(tempj);
            }
            JSONObject msg = new JSONObject();
            msg.put("Catalogue", page+1);
            msg.put("currentpath", newpath);

            tableData.put("msg",msg);
            tableData.put("data",data);



            System.out.println("更新完毕！");
            return tableData;


        }else if("getNewFloder".equals(method)) {
            //创建新的文件夹
            System.out.println("创建新的文件夹");
            String name = request.getParameter("name");
            String newpath = request.getParameter("currentpath");
            String Catalogue = request.getParameter("Catalogue");
            String isfloder = request.getParameter("file");
            int page = Integer.parseInt(Catalogue);
            ResultData rd = fileService.mkDir(newpath, name, page, user);
            rd.writeToResponse(response);
        }
        //暂时
        return tableData;

/**

 //获取分页的参数
 String page = request.getParameter("page");
 String limit = request.getParameter("limit");
 int count = fileService.UserFileCount(user);
 //后面改一下在前端就将数据弄正确
 page = String.valueOf(Integer.parseInt(page)-1);

 System.out.println("用户ID: "+user.getId()+" 分页参数:"+page+" "+limit+" 数据行数："+count);

 //通过请求参数查询数据库
 userFile = fileService.listUserFile(user.getId(),page,limit);

 System.out.println("已经执行sql了");
 System.out.println(userFile.toString());


 //将全部数据的条数作为count传给前台（一共多少条）
 tableData.put("count",count);
 //将分页后的数据返回（每页要显示的数据）
 tableData.put("data", userFile);
 //返回给前端
 return tableData;
 */

    }

    //判断文件是否存在，若存在数据写入数据库

    @RequestMapping(value = "/getmd5",method= RequestMethod.POST)
    public void getMd5(HttpSession session,HttpServletResponse rep, @RequestBody Map<String, String> map){

        //HttpRequest req, HttpResponse rep, HttpSession session
        User user = (User) session.getAttribute("user");
        //System.out.println("用户："+user.getId()+"当前http请求方式为:"+req.getMethod());
        //获取并转换单位
        String filesize = new ByteUnitConversion().readableFileSize(Long.parseLong(map.get("filesize")));
        String filemd5 = map.get("md5value");
        String filename = map.get("filename");
        String currentpath = map.get("currentpath");
        String Catalogue = map.get("Catalogue");
        System.out.println(filemd5+"  "+filesize+" "+filename+" "+currentpath);

        //查询数据库，并将结果放入file
        file = fileService.getFileInfoByMd5(filemd5);

        ResultData rd = new ResultData();

        if (file!=null){
            System.out.println("----------"+file.toString());
            //数据存在
            UserFile uf=new UserFile();
            uf.setFid(file.getId());
            uf.setVfname(filename);
            uf.setUid(user.getId());
            uf.setUptime(TimeUtils.getUtils().getForMatTime());
            uf.setFilesize(filesize);
            //还没想好目录怎么做
            uf.setVpath(currentpath+uf.getVfname());

            System.out.println("------> "+uf.toString());
            fileService.addVFile(uf);
            rd.setData("yes");
            rd.writeToResponse(rep);
        } else {
            System.out.println("数据不存在，准备上传");
            rd.setData("no");
            rd.writeToResponse(rep);
        }


        //return file;
    }

    @RequestMapping(value = "/deletfile",method = RequestMethod.POST)
    @ResponseBody
    public void deluserfile(HttpSession session,HttpServletRequest req,@RequestBody Map<String, String> map){
        User user= (User) session.getAttribute("user");
        System.out.println(map.get("id"));
        UserFile uf = new UserFile();
        uf.setId(Integer.parseInt(map.get("id")));
        fileService.rmDirOrFile(uf,user);

    }

    @RequestMapping(value = "/rename",method = RequestMethod.POST)
    @ResponseBody
    public void rename(HttpSession session,@RequestBody Map<String, String> map){
        User user = (User) session.getAttribute("user");

        UserFile uf = new UserFile();
        uf.setId(Integer.parseInt(map.get("id")));
        uf.setVfname(map.get("rename"));
        System.out.println(uf.getId()+"  "+uf.getVfname());
        fileService.reName(uf,user);
    }


}
