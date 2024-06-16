package com.scut626.wenjuan_king.pojo.view;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scut626.wenjuan_king.pojo.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionDataView {
    public QuestionDataView(Question question)
    {
        this.qid = question.getQid();
        this.questionTitle = question.getQuestionTitle();
        this.questionType = question.getQuestionType();

        List<String> options;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            options = objectMapper.readValue(question.getQuestionOption(), new TypeReference<List<String>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        this.questionOption = options;
    }
    private Integer qid;
    private Integer questionType;
    private String questionTitle;
    private List<String> questionOption;
    private List answerContent;
}
