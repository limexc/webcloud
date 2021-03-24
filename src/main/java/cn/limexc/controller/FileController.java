package cn.limexc.controller;

import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 主要做文件上传下载吧?
 *
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {

    //上传文件
    public void upFile(HttpRequest req, HttpResponse rep, HttpSession session){


    }


    //下载文件
    public void downFile(){

    }

}
