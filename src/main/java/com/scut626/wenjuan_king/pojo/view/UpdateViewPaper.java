package com.scut626.wenjuan_king.pojo.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateViewPaper {
    public UpdateViewPaper(Paper paper)
    {
        this.pid = paper.getPid();
        this.title = paper.getTitle();
        this.status = paper.getStatus();
        this.startTime = paper.getStartTime();
        this.endTime = paper.getEndTime();
        this.createTime = paper.getCreateTime();
        this.accessCount = paper.getAccessCount();
    }
    private Integer pid;
    private String title;
    private Integer status;
    private LocalDate startTime;
    private LocalDate endTime;
    private LocalDateTime createTime;
    private Integer accessCount;
    private List<UpdateViewQuestion> questions;
}
