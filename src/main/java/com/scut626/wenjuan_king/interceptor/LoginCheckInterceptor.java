package com.scut626.wenjuan_king.interceptor;

import com.scut626.wenjuan_king.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("拦截器收到资源请求:" + request.getRequestURI() + ",正在检测是否登录");
        //获取session
        HttpSession session = request.getSession();
        //获取用户属性
        User user = (User)session.getAttribute("user");
        if(user == null)
        {
            //没登录，返回登录页面
            log.info("没有登录，不允许访问！");
            response.sendRedirect("/pages/login.html");
            return false;
        }
        else
        {
            log.info("已经登录了，返回请求的资源");
            //登录了，放行
            return true;
        }
    }
}
