package com.scut626.wenjuan_king.pojo.view;

import com.scut626.wenjuan_king.pojo.Paper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaperPageView {
    private Long paperCount;
    private List<Paper> papers;
}
