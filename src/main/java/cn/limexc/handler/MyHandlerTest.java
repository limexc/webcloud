package cn.limexc.handler;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhiyuanxzy@gmail.com
 * @Description
 * @create 2021-03-09 22:49
 * @since jdk1.8.0
 */
public class MyHandlerTest implements HandlerInterceptor {

    /**
     * preHandler  预处理方法
     * 参数：
     *  Object handler  被拦截的控制器对象
     * 返回值 boolean：
     *  true：
     *  false：
     *
     * 特点：
     *  1.方法在控制器方法前先执行
     *  2.在这个方法中，可以获取请求的信息，验证请求是否符合要求。
     * @param request  用户的请求
     * @param response 响应
     * @param handler  被拦截的控制器对象
     * @return boolean类型
     * @throws Exception 抛出异常
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession()!=null){
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
