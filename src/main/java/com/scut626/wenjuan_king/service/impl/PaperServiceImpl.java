package com.scut626.wenjuan_king.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scut626.wenjuan_king.mapper.PaperMapper;
import com.scut626.wenjuan_king.mapper.QuestionMapper;
import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.view.UpdateViewPaper;
import com.scut626.wenjuan_king.pojo.Question;
import com.scut626.wenjuan_king.pojo.view.UpdateViewQuestion;
import com.scut626.wenjuan_king.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {

    private final PaperMapper paperMapper;

    @Autowired
    public PaperServiceImpl(PaperMapper paperMapper) {
        this.paperMapper = paperMapper;
    }

    @Autowired
    private QuestionMapper questionMapper;


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
    public void insertPaper(UpdateViewPaper paperUpdateInfo){
        //在数据库中插入问卷
        // 获取到问卷信息
        Paper paper = new Paper();
        paper.setTitle(paperUpdateInfo.getTitle());
        paper.setStatus(paperUpdateInfo.getStatus());
        paper.setStartTime(paperUpdateInfo.getStartTime());
        paper.setEndTime(paperUpdateInfo.getEndTime());
        //设置问卷的创建时间
        paper.setCreateTime(LocalDateTime.now());
        //设置问卷的创建用户
            //还没写
        int uid = 15;
        paper.setUid(uid);
        //存入数据库
        paperMapper.insertPaper(paper);
        //获取插入的问卷的id
        int pid = paper.getPid();
        //在数据库中插入所有的问题
        //获取所有的问题
        for (UpdateViewQuestion questionView : paperUpdateInfo.getQuestions()) {
            Question q = new Question(questionView);/*
            //将questionView中的信息传入q
            q.setQuestionType(questionView.getQuestionType());
            q.setQuestionTitle(questionView.getQuestionTitle());
            //将选项集合转为json字符串存入q
            ObjectMapper objectMapper = new ObjectMapper();
            String optionsJson = "";
            try {
                optionsJson = objectMapper.writeValueAsString(questionView.getQuestionOption());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            q.setQuestionOption(optionsJson);*/
            //设置问题对应的问卷id以及创建时间
            q.setPid(pid);
            q.setCreateTime(LocalDateTime.now());
            //插入问题数据
            questionMapper.insertQuestion(q);
        }
    }
    @Override
    public void updatePaper(UpdateViewPaper paperUpdateInfo) {
        //获取要修改的问卷id
        Integer pid = paperUpdateInfo.getPid();
        List<Integer> ids = new ArrayList<>();
        ids.add(pid);

        //删除原先的问卷
        deletePapers(ids);
        //新增一个新的问卷
        insertPaper(paperUpdateInfo);
    }

    /**
     * 查看问卷
     * @param pid
     * @return 问卷信息，如果没找到问卷则返回null
     */
    @Override
    public UpdateViewPaper viewPaper(Integer pid) {
        //根据pid找到问卷
        List<Paper> papersByPid = paperMapper.selectPapersByPid(pid);
        if(papersByPid == null || papersByPid.isEmpty())
        {
            //没找到该id
            return null;
        }
        UpdateViewPaper paperView = new UpdateViewPaper(papersByPid.get(0));
        //根据pid找到问题
        List<Question> questionsByPid = questionMapper.selectQuestionsByPid(pid);
        List<UpdateViewQuestion> questionViews = new ArrayList<>();
        for (Question question : questionsByPid) {
            //把问题封装进view
            UpdateViewQuestion questionView = new UpdateViewQuestion(question);
            questionViews.add(questionView);
        }
        paperView.setQuestions(questionViews);
        return paperView;
    }

}
