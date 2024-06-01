package com.scut626.wenjuan_king.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Answer {
    private Integer aid;
    private Integer pid;
    private Integer qid;
    private Integer uid;
    private LocalDateTime createTime;
    private Integer questionType;
    private String answerContent;
}
