package com.scut626.wenjuan_king.pojo.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerView {
    private Integer qid;
    private Integer questionType;
    private List<String> answerContent;
}
