package com.scut626.wenjuan_king.service;

import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.view.PaperPageView;
import com.scut626.wenjuan_king.pojo.view.UpdateViewPaper;

import java.util.List;

public interface PaperService {
    /**
     * 删除问卷
     * @param pidList 问卷ID列表
     * @return 返回一个数字，
     *              0： 删除成功
     *              1： 删除失败（部分或全部）
     *              2： 删除失败，问卷ID非法（无此问卷）
     */
    int deletePapers(List<Integer> pidList);

    /**
     * 增加一个新问卷
     * @param paperUpdateInfo 传入的问卷信息
     */
    void insertPaper(UpdateViewPaper paperUpdateInfo);

    UpdateViewPaper viewPaper(Integer pid);

    void updatePaper(UpdateViewPaper paperUpdateInfo);

    PaperPageView getPaperList(String name, Integer page, Integer pageSize);
}
