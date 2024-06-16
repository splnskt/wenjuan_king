package com.scut626.wenjuan_king.service.impl;

// 导入必要的类和接口

import com.scut626.wenjuan_king.mapper.AnswerMapper;
import com.scut626.wenjuan_king.mapper.PaperMapper;
import com.scut626.wenjuan_king.mapper.QuestionMapper;
import com.scut626.wenjuan_king.pojo.Answer;
import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.Question;
import com.scut626.wenjuan_king.pojo.view.AnswerPageView;
import com.scut626.wenjuan_king.pojo.view.AnswerView;
import com.scut626.wenjuan_king.pojo.view.PaperDataView;
import com.scut626.wenjuan_king.pojo.view.QuestionDataView;
import com.scut626.wenjuan_king.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    private QuestionMapper questionMapper;

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
     * @param uid            用户ID
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
                answer.setPid(pid);
                answer.setCreateTime(LocalDateTime.now());
                answer.setUid(uid);
                // 插入答案到数据库
                answerMapper.insertAnswer(answer);
            }
        }
        // 增加问卷访问次数
        paperMapper.addPaperAccessCount(pid);
        // 提交成功，返回0
        return 0;
    }

    @Override
    public PaperDataView getPaperData(Integer pid) {
        List<Paper> papers = paperMapper.selectPapersByPid(pid);
        if(papers == null || papers.isEmpty())
        {
            //pid无效
            return null;
        }
        PaperDataView paperDataView = new PaperDataView(papers.get(0));
        //获取问题数据
        List<Question> questions = questionMapper.selectQuestionsByPid(pid);
        //存储问题数据
        List<QuestionDataView> questionDataViews = new ArrayList<>();
        for (Question question : questions) {
            QuestionDataView questionDataView = new QuestionDataView(question);
            //获取这个问题的所有填写
            List<Answer> answers = answerMapper.selectAnswersByQid(question.getQid());
            Integer questionType = questionDataView.getQuestionType();
            if(questionType == 1 || questionType == 2)
            {
                //选择题
                List<Integer> answerContent = new ArrayList<>();
                //获取问题的所有选项
                List<String> questionOption = questionDataView.getQuestionOption();
                //在answercontent中插入与选项个数相同个0
                for (int i = 0; i < questionOption.size(); i++) {
                    answerContent.add(0);
                }
                //统计每个选项选择的个数
                for (Answer answer : answers) {
                    AnswerView answerView = new AnswerView(answer);
                    //分别取答案的每个答案进行比对
                    for (String content : answerView.getAnswerContent()) {
                        //分别与每个选项检测，是否名字相同
                        for (int i = 0; i < questionOption.size(); i++) {
                            if(content.equals(questionOption.get(i)))
                            {
                                //名字相同，结果加1
                                answerContent.set(i, answerContent.get(i) + 1);
                            }
                        }
                    }
                }
                //储存answerContent数据
                questionDataView.setAnswerContent(answerContent);
            }
            else if(questionType == 3)
            {
                //填空题
                List<String> answerContent = new ArrayList<>();
                //统计每个答案
                for (Answer answer : answers) {
                    AnswerView answerView = new AnswerView(answer);
                    List<String> contents = answerView.getAnswerContent();
                    if(contents != null && !contents.isEmpty())
                    {
                        answerContent.add(contents.get(0));
                    }
                }
                //储存answerContent数据
                questionDataView.setAnswerContent(answerContent);
            }
            questionDataViews.add(questionDataView);
        }
        paperDataView.setQuestions(questionDataViews);
        return paperDataView;
    }
}
