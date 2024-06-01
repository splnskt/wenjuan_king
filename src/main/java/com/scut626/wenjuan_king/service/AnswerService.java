package com.scut626.wenjuan_king.service;

import com.scut626.wenjuan_king.pojo.Answer;
import java.util.List;
import java.util.Map;

public interface AnswerService {
    /**
     * 获取指定问卷的所有答案
     * @param pid 问卷ID
     * @return  问卷答案列表
     */
    List<Answer> getAnswersByPid(int pid);

    /**
     * 按问题ID分组答案
     * @param answers 答案列表
     * @return  分组后的答案映射
     */
    Map<Integer, List<Answer>> groupAnswersByQuestion(List<Answer> answers);
}
