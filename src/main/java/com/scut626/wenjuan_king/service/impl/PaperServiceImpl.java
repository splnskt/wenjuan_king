package com.scut626.wenjuan_king.service.impl;

// 导入必要的类和接口
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scut626.wenjuan_king.mapper.PaperMapper;
import com.scut626.wenjuan_king.mapper.QuestionMapper;
import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.view.PaperPageView;
import com.scut626.wenjuan_king.pojo.view.UpdateViewPaper;
import com.scut626.wenjuan_king.pojo.Question;
import com.scut626.wenjuan_king.pojo.view.UpdateViewQuestion;
import com.scut626.wenjuan_king.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 这是一个服务类，实现了PaperService接口
@Service
public class PaperServiceImpl implements PaperService {

    // 使用构造函数注入PaperMapper
    private final PaperMapper paperMapper;

    @Autowired
    public PaperServiceImpl(PaperMapper paperMapper) {
        this.paperMapper = paperMapper;
    }

    // 自动注入QuestionMapper
    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 删除一个问卷
     */
    @Override
    public int deletePapers(List<Integer> pidList) {
        if (pidList == null || pidList.isEmpty()) {
            return 1; // 删除失败，问卷ID列表为空
        }

        int successCount = 0;
        for (int pid : pidList) {
            int result = paperMapper.deletePaperById(pid);
            if (result > 0) {
                successCount++;
            }
        }

        if (successCount == pidList.size()) {
            return 0; // 删除成功
        } else if (successCount == 0) {
            return 2; // 删除失败，问卷ID非法（无此问卷）
        } else {
            return 1; // 部分问卷删除成功，部分删除失败
        }
    }

    /**
     * 增加一个新问卷
     * @param paperUpdateInfo 传入的问卷信息
     */
    @Override
    public void insertPaper(UpdateViewPaper paperUpdateInfo, Integer uid){
        // 创建新的问卷对象并设置相关属性
        Paper paper = new Paper();
        //设置为问卷
        paper.setTemplate(0);
        paper.setTitle(paperUpdateInfo.getTitle());
        paper.setStatus(paperUpdateInfo.getStatus());
        paper.setStartTime(paperUpdateInfo.getStartTime());
        paper.setEndTime(paperUpdateInfo.getEndTime());
        paper.setCreateTime(LocalDateTime.now()); // 设置问卷创建时间
        paper.setUid(uid); // 设置问卷创建用户
        paperMapper.insertPaper(paper); // 存入数据库

        // 获取新插入的问卷的ID
        int pid = paper.getPid();

        // 遍历所有问题并插入到数据库
        for (UpdateViewQuestion questionView : paperUpdateInfo.getQuestions()) {
            Question q = new Question(questionView);
            q.setPid(pid);
            q.setCreateTime(LocalDateTime.now());
            questionMapper.insertQuestion(q);
        }
    }

    @Override
    public void insertTemplate(UpdateViewPaper paperUpdateInfo, Integer uid) {
        // 创建新的问卷对象并设置相关属性
        Paper paper = new Paper();
        //设置为模板
        paper.setTemplate(1);
        paper.setTitle(paperUpdateInfo.getTitle());
        paper.setStatus(paperUpdateInfo.getStatus());
        paper.setStartTime(paperUpdateInfo.getStartTime());
        paper.setEndTime(paperUpdateInfo.getEndTime());
        paper.setCreateTime(LocalDateTime.now()); // 设置问卷创建时间
        paper.setUid(uid); // 设置问卷创建用户
        paperMapper.insertPaper(paper); // 存入数据库

        // 获取新插入的问卷的ID
        int pid = paper.getPid();

        // 遍历所有问题并插入到数据库
        for (UpdateViewQuestion questionView : paperUpdateInfo.getQuestions()) {
            Question q = new Question(questionView);
            q.setPid(pid);
            q.setCreateTime(LocalDateTime.now());
            questionMapper.insertQuestion(q);
        }
    }

    @Override
    public void updateTemplate(UpdateViewPaper paperUpdateInfo, Integer uid) {
        // 获取要修改的问卷ID
        Integer pid = paperUpdateInfo.getPid();
        List<Integer> ids = new ArrayList<>();
        ids.add(pid);

        // 删除原先的问卷
        deletePapers(ids);

        // 新增一个新的模板
        insertTemplate(paperUpdateInfo, uid);
    }

    @Override
    public void like(Integer pid) {
        paperMapper.likePaperByPid(pid);
    }

    /**
     * 更新问卷
     * @param paperUpdateInfo 传入的问卷信息
     * @param uid 用户ID
     */
    @Override
    public void updatePaper(UpdateViewPaper paperUpdateInfo, Integer uid) {
        // 获取要修改的问卷ID
        Integer pid = paperUpdateInfo.getPid();
        List<Integer> ids = new ArrayList<>();
        ids.add(pid);

        // 删除原先的问卷
        deletePapers(ids);

        // 新增一个新的问卷
        insertPaper(paperUpdateInfo, uid);
    }

    /**
     * 查看问卷
     * @param pid 问卷ID
     * @return 问卷信息，如果没找到问卷则返回null
     */
    @Override
    public UpdateViewPaper viewPaper(Integer pid) {
        // 根据问卷ID查找问卷
        List<Paper> papersByPid = paperMapper.selectPapersByPid(pid);
        if (papersByPid == null || papersByPid.isEmpty()) {
            // 如果没有找到对应的问卷ID
            return null;
        }

        UpdateViewPaper paperView = new UpdateViewPaper(papersByPid.get(0));
        // 根据问卷ID查找问题
        List<Question> questionsByPid = questionMapper.selectQuestionsByPid(pid);
        List<UpdateViewQuestion> questionViews = new ArrayList<>();

        // 把问题封装进view对象
        for (Question question : questionsByPid) {
            UpdateViewQuestion questionView = new UpdateViewQuestion(question);
            questionViews.add(questionView);
        }

        paperView.setQuestions(questionViews);
        return paperView;
    }

    /**
     * 查询问卷列表
     * @param name 根据名字查询
     * @param page 第几页
     * @param pageSize 一页有多少问卷
     * @return 返回问卷列表
     */
    @Override
    public PaperPageView getPaperList(String name, Integer page, Integer pageSize, Integer template) {
        if (page != null) {
            page = (page - 1) * pageSize;
        }

        List<Paper> papers = paperMapper.selectPaperList(name, page, pageSize, null, template);
        Long paperCount = paperMapper.paperCount(name, page, pageSize, null, template);
        PaperPageView pageView = new PaperPageView(paperCount, papers);
        return pageView;
    }

    /**
     * 查询用户的问卷列表
     * @param uid 用户ID
     * @param page 第几页
     * @param pageSize 一页有多少问卷
     * @return 返回问卷列表
     */
    @Override
    public PaperPageView myPaperList(Integer uid, Integer page, Integer pageSize, Integer template) {
        if (page != null) {
            page = (page - 1) * pageSize;
        }

        List<Paper> papers = paperMapper.selectPaperList(null, page, pageSize, uid, template);
        Long paperCount = paperMapper.paperCount(null, page, pageSize, uid, template);
        return new PaperPageView(paperCount, papers);
    }

    @Override
    public List<Question> getQuestionsByPid(Integer pid) {
        return questionMapper.selectQuestionsByPid(pid);
    }

}
