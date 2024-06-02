
package com.scut626.wenjuan_king.controller;

// 引入必要的类
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

// 用来实现访问默认地址时跳转到登录页面
@Controller // 声明这是一个 Spring MVC 控制器
public class defaultPageController {

    @RequestMapping("/") // 映射根路径（默认地址）到此方法
    public void jumpToLogin(HttpServletResponse resp) throws IOException {
        // 使用 HttpServletResponse 重定向到登录页面
        resp.sendRedirect("/pages/login.html");
    }
}
