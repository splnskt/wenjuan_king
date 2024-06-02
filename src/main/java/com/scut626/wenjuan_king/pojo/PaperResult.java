package com.scut626.wenjuan_king.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaperResult {
    private int pid;
    private String title;
    private int status;
    private long createTime;
    private String startTime;
    private String endTime;
    private int totalCount;
    private List<QuestionResult> questions;
}
