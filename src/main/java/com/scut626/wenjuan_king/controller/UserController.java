package com.scut626.wenjuan_king.controller;

// 引入必要的类
import com.scut626.wenjuan_king.pojo.Result;
import com.scut626.wenjuan_king.pojo.User;
import com.scut626.wenjuan_king.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j // 自动生成日志记录器
@RestController // 声明该类是一个 REST 控制器，返回 JSON 或 XML 响应
public class UserController {

    @Autowired // 自动注入 UserService 实例
    UserService userService;

    // 处理用户注册请求
    @RequestMapping("/user/register") // 将 /user/register URL 映射到该方法
    public Result register(User user) {
        // 输出注册日志
        log.info("正在注册用户" + user);

        // 调用 userService 的 register 方法进行注册
        boolean flag = userService.register(user);

        // 处理注册结果
        // 注册成功返回成功结果，注册失败返回错误信息
        /*
        if (flag) {
            // 注册成功
            return Result.success();
        } else {
            // 注册失败
            return Result.error("username not available");
        }
        */
        return Result.success(); // 暂时默认返回成功，实际情况应根据 flag 判断
    }

    // 处理用户登录请求
    @RequestMapping("/user/login") // 将 /user/login URL 映射到该方法
    public Result login(User user, HttpServletRequest req) {
        // 输出登录日志
        log.info("用户" + user + "正在登录");

        // 调用 userService 的 login 方法进行登录验证
        int rst = userService.login(user);

        // 根据登录结果返回相应信息
        if (rst == 0) {
            // 登录成功
            log.info("用户" + user + "登录成功");

            // 将用户信息存入 session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            // 返回成功结果
            return Result.success();
        } else if (rst == 1) {
            // 用户名不存在
            return Result.error("username not available");
        } else if (rst == 2) {
            // 密码错误
            return Result.error("password error");
        }

        // 其他未知错误
        return Result.error("Unknown Error");
    }
}
