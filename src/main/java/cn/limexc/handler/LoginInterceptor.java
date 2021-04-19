package cn.limexc.handler;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器拦截所有请求，用于拦截未登录用户将其重定向到登陆界面。
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-03-22 20:23
 * @since jdk1.8.0
 */


public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求的URI
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("user");

        //session不为空,不拦截
        if (obj!=null){
            System.out.println("用户已登录不拦截"+uri);
            return true;
        }

        //system不拦截
        if (uri.indexOf("/system")>0){
            System.out.println("访问system不拦截");
            return true;
        }

        //注册不拦截
        if (uri.indexOf("/register")>0){
            System.out.println("注册不拦截");
            return true;
        }

        //找回密码不拦截
        if (uri.indexOf("/resetpwdpage")>0){
            System.out.println("找回密码不拦截");
            return true;
        }

        //静态文件不拦截
        if (uri.indexOf("/static")>0){
            System.out.println("静态文件不拦截");
            return true;
        }

        //文件分享不拦截
        if (uri.indexOf("/share/file/")>0){
            System.out.println("文件分享界面不拦截");
            return true;
        }

        System.out.println("用户访问的"+uri);

        System.out.println("用户未登录，转跳到登陆页面");
        request.setAttribute("msg", "还没登录，请先登录！");
        request.getRequestDispatcher("/login.jsp").forward(request,response);

        //request.getRequestDispatcher("login.jsp").forward(request,response);
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
