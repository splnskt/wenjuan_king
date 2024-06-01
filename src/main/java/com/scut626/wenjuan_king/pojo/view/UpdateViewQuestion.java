package com.scut626.wenjuan_king.pojo.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateViewQuestion {
    private Integer questionType;
    private String questionTitle;
    private String[] questionOption;
}
