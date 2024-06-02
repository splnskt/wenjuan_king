package com.scut626.wenjuan_king.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaperResult {
    private Integer pid;
    private String title;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDate startTime;
    private LocalDate endTime;
    private Integer totalCount;
    private List<QuestionResult> questions;
}
