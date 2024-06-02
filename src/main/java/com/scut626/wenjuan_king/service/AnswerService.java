package com.scut626.wenjuan_king.service;

import com.scut626.wenjuan_king.pojo.Answer;
import com.scut626.wenjuan_king.pojo.view.AnswerPageView;

import java.util.List;
import java.util.Map;

public interface AnswerService {
    /**
     * 获取指定问卷的所有答案
     * @param pid 问卷ID
     * @return 问卷答案列表
     */
    List<Answer> getAnswersByPid(int pid);

    /**
     * 按问题ID分组答案
     * @param answers 答案列表
     * @return 分组后的答案映射，以问题ID为键，答案列表为值
     */
    Map<Integer, List<Answer>> groupAnswersByQuestion(List<Answer> answers);

    /**
     * 提交问卷答案
     * @param answerPageView 传入的答卷数据
     * @param uid 用户ID
     * @return 返回提交结果，
     *              0：提交成功
     *              1：问卷ID不存在
     *              2：其他错误（如空提交）
     */
    int commitPaper(AnswerPageView answerPageView, Integer uid);
}
