package cn.limexc.controller;

import cn.limexc.model.Massage;
import cn.limexc.model.User;
import cn.limexc.service.MsgService;
import cn.limexc.service.UserService;
import cn.limexc.util.ResultData;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-05-18 15:59
 * @since jdk1.8.0
 */
@Controller
@RequestMapping(value = "/msg")
public class MassageController {

    @Resource
    private MsgService msgService;
    @Resource
    private UserService userService;

    @RequestMapping("/create")
    public String createMsgPage(){
        return "createmsg";
    }


    @RequestMapping("/usermsglist")
    @ResponseBody
    public Map<String,Object> getUserMsgList(HttpSession session){
        User user = (User) session.getAttribute("user");
        Map<String, Object> msgData = new HashMap<String, Object>();
        msgData.put("code", 0);
        msgData.put("msg","");
        List<Massage> msgs = msgService.listUserMsg(user);

        JSONArray data =new JSONArray();
        for (Massage msg:msgs) {
            JSONObject jsonMsg = new JSONObject();
            jsonMsg.put("id",msg.getId());
            jsonMsg.put("status",msg.getStatus());
            jsonMsg.put("title",msg.getTitle());
            jsonMsg.put("create_time",msg.getCreate_time());
            jsonMsg.put("info",msg.getInfo());
            jsonMsg.put("size",msg.getSize());
            jsonMsg.put("replay",msg.getReply());
            data.add(jsonMsg);
        }

        msgData.put("data",data);
        return msgData;
    }

    @RequestMapping("/del_msg")
    @ResponseBody
    public void delMsg(HttpSession session, HttpServletRequest req, HttpServletResponse rep,@RequestBody Map jsmp){
        User user = (User) session.getAttribute("user");
        Integer id = (Integer)jsmp.get("id");
        ResultData rd = new ResultData();
        if (msgService.delMsg(user,id)){
            rd.setData("ok");
        }else {
            rd.setData("err");
        }
        rd.writeToResponse(rep);
    }

    @RequestMapping("/req_msg")
    @ResponseBody
    public void reqMsg(HttpSession session, HttpServletRequest req, HttpServletResponse rep,@RequestBody Map jsmp){
        User user = (User) session.getAttribute("user");
        //{"title":"asd","info":"asd","size":"aa"}
        Massage msg = new Massage();
        msg.setUid(user.getId());
        msg.setInfo((String) jsmp.get("info"));
        msg.setTitle((String) jsmp.get("title"));
        if (jsmp.get("size")!=null){
            msg.setSize((String) jsmp.get("size"));
        }
        //System.out.printf(msg.toString());
        ResultData rd = new ResultData();
        if (msgService.createMsg(msg)){
            rd.setData("ok");
        }else {
            rd.setData("err");
        }
        rd.writeToResponse(rep);
    }



}
