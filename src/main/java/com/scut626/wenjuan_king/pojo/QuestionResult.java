package com.scut626.wenjuan_king.pojo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionResult {
    private Integer qid;
    private Integer questionType;
    private String questionTitle;
    private List<String> questionOption;
    private List<Object> answerContent;
}
