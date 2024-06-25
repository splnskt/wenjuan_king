package com.scut626.wenjuan_king.controller;

// 引入必要的类
import com.scut626.wenjuan_king.pojo.Result;
import com.scut626.wenjuan_king.pojo.User;
import com.scut626.wenjuan_king.pojo.view.PaperPageView;
import com.scut626.wenjuan_king.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        } else if (rst == 3) {
            //用户被封禁
            return Result.error("user banned");
        } else if (rst == 4) {
            //用户是管理员
            // 登录成功
            log.info("管理员" + user + "登录成功");

            // 将用户信息存入 session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);

            // 返回成功结果
            return Result.success("admin");
        }

        // 其他未知错误
        return Result.error("Unknown Error");
    }
    //退出登录
    @RequestMapping("/user/logout")
    public Result logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect("/pages/login.html");
        return Result.success();
    }
    @RequestMapping("/user/upload-image")
    public Result uploadImage(HttpServletRequest req, MultipartFile image) throws IOException {
        log.info("正在上传头像");
        String originalFilename = image.getOriginalFilename();
        originalFilename = "\\user_image\\" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + originalFilename;

        image.transferTo(new File("D:\\1\\WebProgram\\wenjuan_king\\src\\main\\resources\\static" + originalFilename));
        // 获取当前会话
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        // 获取用户ID
        Integer uid = user.getUid();
        if(uid == null)
            return Result.nologin();
        //把路径中\改成/
        originalFilename = originalFilename.replace("\\", "/");
        userService.updateImageUriByUid(originalFilename, uid);

        return Result.success();
    }
    @RequestMapping("/user/image")
    public Result getImage(HttpServletRequest req, MultipartFile image) throws IOException {
        log.info("正在获取头像");
        // 获取当前会话
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        // 获取用户ID
        if(user == null)
            return Result.nologin();
        Integer uid = user.getUid();
        String uri = userService.selectImageUriByUid(uid);
        return Result.success(uri);
    }
    @RequestMapping("/user/all-user")
    public Result viewUserList(String name, Integer page, Integer pageSize) {
        log.info("查找用户列表...");
        // 调用服务层方法获取问卷列表
        List<User> userList = userService.userList(name, page, pageSize);
        //获取用户数量
        Long userCount = userService.getUserCount(name);
        //包装两个数据
        Map<String, Object> map = new HashMap<>();
        map.put("userCount", userCount);
        map.put("users", userList);
        return Result.success(map);
    }
    @RequestMapping("/user/delete-user")
    public Result deleteUser(Integer uid) {
        log.info("删除用户...");
        // 调用服务层方法获取问卷列表
        userService.deleteUser(uid);
        return Result.success();
    }
    @RequestMapping("/user/ban-user")
    public Result banUser(Integer uid) {
        log.info("封禁用户" + uid + "...");
        // 调用服务层方法获取问卷列表
        userService.setUserBanned(uid, 1);
        return Result.success();
    }
    @RequestMapping("/user/unban-user")
    public Result unbanUser(Integer uid) {
        log.info("解除封禁用户" + uid + "...");
        // 调用服务层方法获取问卷列表
        userService.setUserBanned(uid, 0);
        return Result.success();
    }
}
