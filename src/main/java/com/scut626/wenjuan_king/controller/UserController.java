package com.scut626.wenjuan_king.controller;

import com.scut626.wenjuan_king.pojo.Result;
import com.scut626.wenjuan_king.pojo.User;
import com.scut626.wenjuan_king.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;
    @RequestMapping("/user/register")
    public Result register(User user)
    {
        //输出日志
        log.info("正在注册用户" + user);
        //
        boolean flag = userService.register(user);
        /*if(flag)
        {
            //注册成功
            return Result.success();
        }
        else
        {
            //注册失败
            return Result.error("username not available");
        }*/
        return Result.success();
    }
    @RequestMapping("/user/login")
    public Result login(User user, HttpServletRequest req)
    {
        //输出日志
        log.info("用户" + user + "正在登录");
        //
        int rst = userService.login(user);
        if(rst == 0)
        {
            //登录成功
            //输出日志
            log.info("用户" + user + "登录成功");
            //
            //将用户信息放入session中
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            //返回登录成功信息
            return Result.success();
        }
        else if(rst == 1)
        {
            //用户名不存在
            return Result.error("username not available");
        }
        else if (rst == 2)
        {
            //密码错误
            return Result.error("password error");
        }
        return Result.error("Unknown Error");
    }
}
