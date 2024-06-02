package com.scut626.wenjuan_king.service.impl;

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

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private PaperMapper paperMapper;

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

    /**
     * 执行提交问卷的业务
     *
     * @param answerPageView 传来的答卷数据
     * @return 返回提交结果，
     * 0: 提交成功
     * 1：问卷id不存在
     * 2: 其他错误
     */
    @Override
    public int commitPaper(AnswerPageView answerPageView, Integer uid) {
        //检查问卷id是否存在
        Integer pid = answerPageView.getPid();
        List<Paper> papers = paperMapper.selectPapersByPid(pid);
        if (papers == null || papers.isEmpty()) {
            //问卷不存在
            return 1;
        }
        //获取答案数据
        List<AnswerView> answers = answerPageView.getAnswers();
        if (answers == null || answers.isEmpty()) {
            //空提交
            return 2;
        } else
            for (AnswerView answerView : answerPageView.getAnswers()) {
                //创建一条答案数据
                Answer answer = new Answer(answerView);
                //设置答案数据的pid等信息
                answer.setPid(answerPageView.getPid());
                answer.setCreateTime(LocalDateTime.now());
                answer.setUid(uid);
                //插入答案数据到数据库
                answerMapper.insertAnswer(answer);
            }
        return 0;
    }
}
