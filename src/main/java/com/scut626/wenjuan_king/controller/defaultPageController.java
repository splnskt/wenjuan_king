package com.scut626.wenjuan_king.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

//用来实现访问默认地址时跳转到登录页面
@Controller
public class defaultPageController {
    @RequestMapping("/")
    public void jumpToLogin(HttpServletResponse resp) throws IOException {
        resp.sendRedirect("/pages/login.html");
    }
}
