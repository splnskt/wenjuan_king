package com.scut626.wenjuan_king.service;

import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.Question;
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
     * @param uid 用户ID
     */
    void insertPaper(UpdateViewPaper paperUpdateInfo, Integer uid);

    /**
     * 查看问卷
     * @param pid 问卷ID
     * @return 返回问卷详细信息，如果没找到问卷则返回null
     */
    UpdateViewPaper viewPaper(Integer pid);

    /**
     * 更新问卷
     * @param paperUpdateInfo 传入的问卷信息
     * @param uid 用户ID
     */
    void updatePaper(UpdateViewPaper paperUpdateInfo, Integer uid);

    /**
     * 查询问卷列表
     * @param name 根据名字查询
     * @param page 第几页
     * @param pageSize 每页问卷数量
     * @return 返回问卷列表的分页视图
     */
    PaperPageView getPaperList(String name, Integer page, Integer pageSize);

    /**
     * 查询用户的问卷列表
     * @param uid 用户ID
     * @param page 第几页
     * @param pageSize 每页问卷数量
     * @return 返回用户问卷列表的分页视图
     */
    PaperPageView myPaperList(Integer uid, Integer page, Integer pageSize);

    /**
     * 由问卷id查询问题
     * @param pid paperID
     * @return 返回用户问卷列表的分页视图
     */
    List<Question> getQuestionsByPid(Integer pid);
}

