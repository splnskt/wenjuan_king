package com.scut626.wenjuan_king.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Question {
    private Integer qid;
    private Integer pid;
    private LocalDateTime createTime;
    private Integer questionType;
    private String questionTitle;
    private String questionOption;
}
