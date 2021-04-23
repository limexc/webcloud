package cn.limexc.handler;

import cn.limexc.model.Group;
import cn.limexc.model.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthorityInterceptor implements HandlerInterceptor {

    /**
     * 拦截与用户组权限相关的请求
     *
     * 正在思考到底需不需要。
     * @param request  请求
     * @param response 响应
     * @param handler
     * @return
     * @throws Exception
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求的URI
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Group group = (Group) session.getAttribute("group");

        System.out.println("请求的url："+uri+"用户名与权限："+user.getUsername()+"  "+group.getPower());
        //power=0为管理员
        if (group.getPower()==0){
            System.out.println("允许");
            return true;
        }


        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
