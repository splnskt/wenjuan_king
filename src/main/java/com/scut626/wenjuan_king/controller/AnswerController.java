package com.scut626.wenjuan_king.controller;

// 引入必要的类

import com.scut626.wenjuan_king.mapper.PaperMapper;
import com.scut626.wenjuan_king.pojo.*;
import com.scut626.wenjuan_king.pojo.view.AnswerPageView;
import com.scut626.wenjuan_king.pojo.view.PaperDataView;
import com.scut626.wenjuan_king.pojo.view.UpdateViewPaper;
import com.scut626.wenjuan_king.pojo.view.UpdateViewQuestion;
import com.scut626.wenjuan_king.service.AnswerService;
import com.scut626.wenjuan_king.service.PaperService;
import com.scut626.wenjuan_king.service.impl.PaperServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j // 生成日志记录器的注解
@RestController // 表示这是一个控制器，并且所有方法都返回一个 JSON 或 XML 响应
@RequestMapping("/answer") // 指定基础 URL 路径
public class AnswerController {

    @Autowired // 自动注入 AnswerService 实例
    private AnswerService answerService;
    @Autowired
    private PaperService paperService;

    /**
     * 处理填写问卷的请求
     *
     * @param answerPageView 接受的前端传来的json串信息
     * @return 返回一个表示执行是否成功的结果
     */
    @PostMapping("/commit-paper")
    public Result commitPaper(@RequestBody AnswerPageView answerPageView, HttpServletRequest req) {
        log.info("正在提交问卷:{}的答案...", answerPageView.getPid());
        //获取session中的uid
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.error("no login");
        }
        int uid = user.getUid();
        int flag = answerService.commitPaper(answerPageView, uid);
        if (flag == 0) {
            //提交成功
            return Result.success();
        } else if (flag == 1) {
            //问卷不存在
            return Result.error("no such paper");
        } else {
            //其他错误
            return Result.error("unknown error");
        }
    }

    /**
     * 查看问卷数据
     *
     * @param pid 问卷ID
     * @return 返回问卷详细信息
     */
    @PostMapping("/paper-data") // 指定处理 POST 请求的 URL 路径
    public Result getPaperData(Integer pid, HttpServletRequest req) {
        log.info("正在查看问卷:{}的数据...", pid);
        PaperDataView paperDataView = answerService.getPaperData(pid);
        if (paperDataView != null)
            return Result.success(paperDataView);
        else
            return Result.error("wrong pid");
    }
}

//// 按问题ID分组答案
//Map<Integer, List<Answer>> groupedAnswers = answerService.groupAnswersByQuestion(answers);
//
//// 构建 QuestionResult 列表
//List<QuestionResult> questionResults = new ArrayList<>();
//            for (Map.Entry<Integer, List<Answer>> entry : groupedAnswers.entrySet()) {
//Integer qid = entry.getKey();
//
//List<Answer> answerList = entry.getValue();
//
//                if (answerList.isEmpty()) continue;
//
//Answer firstAnswer = answerList.getFirst();
//int questionType = firstAnswer.getQuestionType();
//String questionTitle = firstAnswer.getQuestionTitle();
//List<String> questionOption = Arrays.asList(firstAnswer.getQuestionOption().split(","));
//List<Object> answerContent;
//
//                if (questionType == 1 || questionType == 2) {
//// 统计选择题的答案内容
//Map<String, Long> optionCount = answerList.stream()
//        .collect(Collectors.groupingBy(Answer::getAnswerContent, Collectors.counting()));
//answerContent = questionOption.stream()
//                            .map(option -> optionCount.getOrDefault(option, 0L))
//        .collect(Collectors.toList());
//        } else {
//// 收集简答题的答案内容
//answerContent = answerList.stream()
//                            .map(Answer::getAnswerContent)
//                            .collect(Collectors.toList());
//        }
//
//QuestionResult questionResult = new QuestionResult(qid, questionType, questionTitle, questionOption, answerContent);
//                questionResults.add(questionResult);
//            }
//
//                    // 设置问卷的 questions 字段
//                    data.setQuestions(questionResults);
//
//
////     List<QuestionResult> questionResults = new ArrayList<>();


