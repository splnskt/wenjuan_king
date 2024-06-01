package com.scut626.wenjuan_king.controller;

import com.scut626.wenjuan_king.pojo.Answer;
import com.scut626.wenjuan_king.pojo.Result;
import com.scut626.wenjuan_king.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("/paper-data")
    public Result getPaperData(@RequestBody Map<String, Integer> request) {
        int pid = request.get("pid");

        // 输出日志
        log.info("正在获取问卷数据，问卷ID：" + pid);

        try {
            List<Answer> answers = answerService.getAnswersByPid(pid);
            Map<Integer, List<Answer>> groupedAnswers = answerService.groupAnswersByQuestion(answers);

            // 构建返回数据结构
            Map<String, Object> data = new HashMap<>();
            data.put("pid", pid);
            data.put("title", "Mock Title");  // 这里应该替换成实际的问卷标题
            data.put("status", 1);  // 这里应该替换成实际的问卷状态
            data.put("createTime", System.currentTimeMillis());  // 这里应该替换成实际的问卷创建时间
            data.put("startTime", "2023-01-01");  // 这里应该替换成实际的问卷开始时间
            data.put("endTime", "2023-12-31");  // 这里应该替换成实际的问卷结束时间
            data.put("totalCount", answers.size());

            List<Map<String, Object>> questions = groupedAnswers.entrySet().stream().map(entry -> {
                Map<String, Object> questionData = new HashMap<>();
                questionData.put("qid", entry.getKey());
                questionData.put("questionType", entry.getValue().get(0).getQuestionType());
                questionData.put("questionTitle", "Mock Question Title");  // 这里应该替换成实际的问题标题
                questionData.put("questionOption", List.of("Option1", "Option2"));  // 这里应该替换成实际的选项
                questionData.put("answerContent", entry.getValue().stream().map(Answer::getAnswerContent).collect(Collectors.toList()));
                return questionData;
            }).collect(Collectors.toList());

            data.put("questions", questions);

            // 返回成功结果
            return Result.success(data);
        } catch (Exception e) {
            log.error("获取问卷数据失败，问卷ID：" + pid, e);
            return Result.error("Failed to retrieve paper data");
        }
    }
}
