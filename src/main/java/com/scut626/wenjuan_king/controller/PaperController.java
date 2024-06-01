package com.scut626.wenjuan_king.controller;

import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.User;
import com.scut626.wenjuan_king.pojo.view.PaperPageView;
import com.scut626.wenjuan_king.pojo.view.UpdateViewPaper;
import com.scut626.wenjuan_king.pojo.Result;
import com.scut626.wenjuan_king.service.PaperService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * PaperController类用于处理与问卷相关的HTTP请求。
 */
@Slf4j // 用于记录日志
@RestController // 声明这是一个控制器类，并且每个方法的返回值会自动转换为JSON格式
@RequestMapping("/paper") // 基础请求路径
public class PaperController {

    private final PaperService paperService;

    @Autowired // 自动注入PaperService依赖
    public PaperController(PaperService paperService) {
        this.paperService = paperService;
    }

    /**
     * 删除问卷接口
     * @param request 包含问卷ID列表的请求体
     * @return 操作结果
     */
    @PostMapping("/delete-paper")
    public Result deletePaper(@RequestBody Map<String, Object> request) {
        // 获取请求体中的问卷ID列表
        List<Integer> pidList = (List<Integer>) request.get("pidList");
        if (pidList == null || pidList.isEmpty()) {
            return Result.error("删除问卷失败，缺少问卷ID参数"); // 检查ID列表是否为空
        }
        // 输出日志信息
        log.info("正在删除id为" + pidList + "的问卷");

        // 调用服务层删除问卷方法
        int resultCode = paperService.deletePapers(pidList);
        // 根据结果代码返回不同的结果
        switch (resultCode) {
            case 0:
                return Result.success("删除问卷成功");
            case 1:
                return Result.error("删除问卷失败");
            case 2:
                return Result.error("删除问卷失败，问卷ID非法（无此问卷）");
            default:
                return Result.error("删除问卷失败，未知错误");
        }
    }

    /**
     * 增加一个新问卷
     * @param paperUpdateInfo 传入的问卷信息
     * @param req HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/update-paper")
    public Result insertPaper(@RequestBody UpdateViewPaper paperUpdateInfo, HttpServletRequest req) {
        // 获取当前会话
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        // 获取用户ID
        Integer uid = user.getUid();
        // 输出日志信息
        log.info("正在增加问卷：" + paperUpdateInfo.getTitle());
        // 调用服务层方法插入问卷
        paperService.insertPaper(paperUpdateInfo, uid);
        return Result.success();
    }

    /**
     * 更新问卷
     * @param paperUpdateInfo 传入的问卷信息
     * @param req HTTP请求对象
     * @return 操作结果
     */
    @PutMapping("/update-paper")
    public Result updatePaper(@RequestBody UpdateViewPaper paperUpdateInfo, HttpServletRequest req) {
        // 获取当前会话
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        // 获取用户ID
        Integer uid = user.getUid();
        // 输出日志信息
        log.info("正在修改问卷：" + paperUpdateInfo.getTitle());
        // 调用服务层方法更新问卷
        paperService.updatePaper(paperUpdateInfo, uid);
        return Result.success();
    }

    /**
     * 查看问卷（供修改使用）
     * @param pid 问卷ID
     * @return 操作结果
     */
    @PostMapping("/view-paper")
    public Result adminViewPaper(Integer pid) {
        // 输出日志信息
        log.info("正在获取问卷编号为：" + pid);
        // 调用服务层方法获取问卷信息
        UpdateViewPaper paperView = paperService.viewPaper(pid);
        if (paperView == null) {
            log.info("要查看的问卷不存在");
            return Result.error("paperID not exist"); // 问卷不存在
        } else {
            return Result.success(paperView); // 返回问卷信息
        }
    }

    /**
     * 查看问卷列表
     * @param name 问卷名称
     * @param page 当前页码
     * @param pageSize 每页显示数量
     * @return 操作结果
     */
    @RequestMapping("/paper-lists")
    public Result viewPaperList(String name, Integer page, Integer pageSize) {
        log.info("查找问卷...");
        // 调用服务层方法获取问卷列表
        PaperPageView paperPageView = paperService.getPaperList(name, page, pageSize);
        return Result.success(paperPageView);
    }

    /**
     * 查看当前用户的问卷列表
     * @param req HTTP请求对象
     * @param page 当前页码
     * @param pageSize 每页显示数量
     * @return 操作结果
     */
    @RequestMapping("/my-papers")
    public Result myPaperList(HttpServletRequest req, Integer page, Integer pageSize) {
        log.info("查找我的问卷...");
        // 获取当前会话
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        // 获取用户ID
        Integer uid;
        if (user != null) {
            uid = user.getUid();
            // 调用服务层方法获取用户的问卷列表
            PaperPageView paperPageView = paperService.myPaperList(uid, page, pageSize);
            return Result.success(paperPageView);
        } else {
            return Result.error("no login"); // 用户未登录
        }
    }
}
