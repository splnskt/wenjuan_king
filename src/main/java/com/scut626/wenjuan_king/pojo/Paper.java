package com.scut626.wenjuan_king.pojo;

import com.scut626.wenjuan_king.pojo.view.UpdateViewPaper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Paper {
    public Paper(UpdateViewPaper paperView)
    {
        this.pid = paperView.getPid();
        this.title = paperView.getTitle();
        this.status = paperView.getStatus();
        this.startTime = paperView.getStartTime();
        this.endTime = paperView.getEndTime();
        this.createTime = paperView.getCreateTime();
        this.accessCount = paperView.getAccessCount();
    }

    private Integer pid;
    private Integer uid;
    private String title;
    private Integer status;
    private Integer accessCount;
    private LocalDateTime createTime;
    private LocalDate startTime;
    private LocalDate endTime;
}
