package com.scut626.wenjuan_king.service.impl;

// 导入必要的类和接口
import com.scut626.wenjuan_king.mapper.AnswerMapper;
import com.scut626.wenjuan_king.mapper.PaperMapper;
import com.scut626.wenjuan_king.pojo.Answer;
import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.view.AnswerPageView;
import com.scut626.wenjuan_king.pojo.view.AnswerView;
import com.scut626.wenjuan_king.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 表示这是一个服务类，实现了AnswerService接口
@Service
public class AnswerServiceImpl implements AnswerService {

    // 自动注入AnswerMapper和PaperMapper
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private PaperMapper paperMapper;

    // 根据问卷ID获取答案列表
    @Override
    public List<Answer> getAnswersByPid(int pid) {
        // 输出日志
        System.out.println("正在获取问卷ID为 " + pid + " 的答案");
        // 调用AnswerMapper的方法从数据库中获取答案列表
        return answerMapper.selectAnswersByPid(pid);
    }

    // 将答案按问题ID分组
    @Override
    public Map<Integer, List<Answer>> groupAnswersByQuestion(List<Answer> answers) {
        // 使用Java Stream API按问题ID分组答案
        return answers.stream().collect(Collectors.groupingBy(Answer::getQid));
    }

    /**
     * 执行提交问卷的业务逻辑
     *
     * @param answerPageView 传来的答卷数据
     * @param uid 用户ID
     * @return 返回提交结果，
     * 0: 提交成功
     * 1: 问卷ID不存在
     * 2: 其他错误
     */
    @Override
    public int commitPaper(AnswerPageView answerPageView, Integer uid) {
        // 检查问卷ID是否存在
        Integer pid = answerPageView.getPid();
        List<Paper> papers = paperMapper.selectPapersByPid(pid);
        if (papers == null || papers.isEmpty()) {
            // 如果问卷不存在，返回1
            return 1;
        }
        // 获取答案数据
        List<AnswerView> answers = answerPageView.getAnswers();
        if (answers == null || answers.isEmpty()) {
            // 如果提交的答案为空，返回2
            return 2;
        } else {
            // 遍历每个答案视图
            for (AnswerView answerView : answerPageView.getAnswers()) {
                // 创建Answer对象，并设置相关属性
                Answer answer = new Answer(answerView);
                answer.setPid(answerPageView.getPid());
                answer.setCreateTime(LocalDateTime.now());
                answer.setUid(uid);
                // 插入答案到数据库
                answerMapper.insertAnswer(answer);
            }
        }
        // 提交成功，返回0
        return 0;
    }
}
