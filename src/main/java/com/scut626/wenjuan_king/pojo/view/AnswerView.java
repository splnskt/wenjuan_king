package com.scut626.wenjuan_king.pojo.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scut626.wenjuan_king.pojo.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerView {
    public AnswerView(Answer answer)
    {
        this.qid = answer.getQid();
        this.questionType = answer.getQuestionType();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            answerContent = objectMapper.readValue(answer.getAnswerContent(), new TypeReference<List<String>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private Integer qid;
    private Integer questionType;
    private List<String> answerContent;
}
