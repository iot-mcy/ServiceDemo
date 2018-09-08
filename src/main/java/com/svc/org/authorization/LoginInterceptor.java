package com.svc.org.authorization;

import com.svc.org.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(LoginInterceptor.class.getSimpleName());

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取请求的URL
        String url = request.getRequestURI();
        String token = request.getHeader(Constants.DEFAULT_TOKEN_NAME);

        request.setCharacterEncoding("UTF-8");

        System.out.println("post URL：" + url);

        if (url.contains("/user.svc/login")) {
            return true;
        }

        if (token != null) {
            return true;

        }

        // 不符合条件的，跳转到登录界面
        logger.error("没有登陆权限，跳转到登陆界面");
        // request.getRequestDispatcher("/WEB-INF/view/user/login.html").forward(request,
        // response);

//        response.sendRedirect(request.getContextPath() + "/login");

        response.setStatus(500);

        return false;

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
