package com.scut626.wenjuan_king.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scut626.wenjuan_king.pojo.view.UpdateViewQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    public Question(UpdateViewQuestion questionView){
        this.qid = questionView.getQid();
        this.questionTitle = questionView.getQuestionTitle();
        this.questionType = questionView.getQuestionType();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            questionOption = objectMapper.writeValueAsString(questionView.getQuestionOption());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private Integer qid;
    private Integer pid;
    private LocalDateTime createTime;
    private Integer questionType;
    private String questionTitle;
    private String questionOption;
}
