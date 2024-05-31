package com.scut626.wenjuan_king.service;

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
}
