package com.scut626.wenjuan_king.controller;

// 引入必要的类
import com.scut626.wenjuan_king.pojo.Answer;
import com.scut626.wenjuan_king.pojo.Result;
import com.scut626.wenjuan_king.pojo.User;
import com.scut626.wenjuan_king.pojo.QuestionResult;
import com.scut626.wenjuan_king.pojo.PaperResult;
import com.scut626.wenjuan_king.pojo.view.AnswerPageView;
import com.scut626.wenjuan_king.service.AnswerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j // 生成日志记录器的注解
@RestController // 表示这是一个控制器，并且所有方法都返回一个 JSON 或 XML 响应
@RequestMapping("/answer") // 指定基础 URL 路径
public class AnswerController {

    @Autowired // 自动注入 AnswerService 实例
    private AnswerService answerService;

    /**
     * 处理填写问卷的请求
     * @param answerPageView    接受的前端传来的json串信息
     * @return                  返回一个表示执行是否成功的结果
     */
    @PostMapping("/commit-paper")
    public Result commitPaper(@RequestBody AnswerPageView answerPageView, HttpServletRequest req)
    {
        log.info("正在提交问卷:{}的答案...", answerPageView.getPid());
        //获取session中的uid
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null)
        {
            return Result.error("no login");
        }
        int uid = user.getUid();
        int flag = answerService.commitPaper(answerPageView, uid);
        if(flag == 0){
            //提交成功
            return Result.success();
        }
        else if(flag == 1){
            //问卷不存在
            return Result.error("no such paper");
        }
        else{
            //其他错误
            return Result.error("unknown error");
        }
    }

//    @PostMapping("/paper-data") // 指定处理 POST 请求的 URL 路径
//    public Result getPaperData(int pid) {
//
//
//        // 输出日志
//        log.info("正在获取问卷数据，问卷ID：" + pid);
//
//        try {
//            // 通过问卷 ID 获取所有答案
//            List<Answer> answers = answerService.getAnswersByPid(pid);
//            // 将答案按问题 ID 分组
//            Map<Integer, List<Answer>> groupedAnswers = answerService.groupAnswersByQuestion(answers);
//
//
//
//            // 返回成功结果
//            return Result.success(data);
//        } catch (Exception e) {
//            // 输出错误日志
//            log.error("获取问卷数据失败，问卷ID：" + pid, e);
//            // 返回错误结果
//            return Result.error("Failed to retrieve paper data");
//
//        }
//    }
}
