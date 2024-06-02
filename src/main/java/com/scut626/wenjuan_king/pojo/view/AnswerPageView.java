package com.scut626.wenjuan_king.pojo.view;

import com.scut626.wenjuan_king.pojo.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnswerPageView {
    private Integer pid;
    private List<AnswerView> answers;
}
