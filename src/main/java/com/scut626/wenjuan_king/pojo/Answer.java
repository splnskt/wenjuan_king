package com.scut626.wenjuan_king.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scut626.wenjuan_king.pojo.view.AnswerView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Answer {
    /**
     * 通过answerView来初始化Answer
     *
     * @param answerView
     */
    public Answer(AnswerView answerView) {
        this.qid = answerView.getQid();
        this.questionType = answerView.getQuestionType();
        //将数组转为json串
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<String> answerContentList = answerView.getAnswerContent();
            if (answerContentList == null || answerContentList.isEmpty()) {
                //答案为空
                this.answerContent = "[]";
            } else {
                this.answerContent = objectMapper.writeValueAsString(answerView.getAnswerContent());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer aid;
    private Integer pid;
    private Integer qid;
    private Integer uid;
    private LocalDateTime createTime;
    private Integer questionType;
    private String answerContent;
}
