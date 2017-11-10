package kaizone.songmaya.qingtian.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        // user拦截
        if (!request.getRequestURI().startsWith("/user")) {
            return true;
        }
        //登录不做拦截
        if (request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/login_view")) {
            return true;
        }
        //
        Object obj = request.getSession().getAttribute("_session_user");
        if (obj == null) {
            response.sendRedirect("/user/login_view");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
