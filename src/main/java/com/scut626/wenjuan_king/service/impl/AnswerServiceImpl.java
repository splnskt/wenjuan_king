package com.scut626.wenjuan_king.service.impl;

import com.scut626.wenjuan_king.mapper.AnswerMapper;
import com.scut626.wenjuan_king.pojo.Answer;
import com.scut626.wenjuan_king.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public List<Answer> getAnswersByPid(int pid) {
        // 输出日志
        System.out.println("正在获取问卷ID为 " + pid + " 的答案");
        // 调用Mapper方法获取答案列表
        return answerMapper.selectAnswersByPid(pid);
    }

    @Override
    public Map<Integer, List<Answer>> groupAnswersByQuestion(List<Answer> answers) {
        // 按问题ID分组答案
        return answers.stream().collect(Collectors.groupingBy(Answer::getQid));
    }
}
