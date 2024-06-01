package com.scut626.wenjuan_king;

import com.scut626.wenjuan_king.mapper.AnswerMapper;
import com.scut626.wenjuan_king.mapper.PaperMapper;
import com.scut626.wenjuan_king.mapper.QuestionMapper;
import com.scut626.wenjuan_king.mapper.UserMapper;
import com.scut626.wenjuan_king.pojo.Answer;
import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.Question;
import com.scut626.wenjuan_king.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class WenjuanKingApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Autowired
    PaperMapper paperMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    AnswerMapper answerMapper;


    //region TestMappers
    @Test
    void testUserMapper() {
        User user = new User();
        user.setUsername("miumiu");
        user.setPassword("123456");
        user.setCreateTime(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());

        userMapper.insertUser(user);
        for (User u : userMapper.selectAllUsers()) {
            System.out.println(u);
        }
    }
    @Test
    void testPaperMapper() {
        Paper paper = new Paper();
        paper.setUid(1);
        paper.setTitle("testPaper");
        paper.setStatus(1);
        paper.setCreateTime(LocalDateTime.now());
        paper.setStartTime(LocalDateTime.now());
        paper.setEndTime(LocalDateTime.now().plusDays(7));

        paperMapper.insertPaper(paper);
        for (Paper p : paperMapper.selectAllPapers()) {
            System.out.println(p);
        }
    }
    @Test
    void testQuestionMapper() {
        Question question = new Question(
                        null,
                        1,
                        LocalDateTime.now(),
                        1,
                        "test question",
                        "[\"option1\", \"option2\"]");

        questionMapper.insertQuestion(question);
        for (Question q : questionMapper.selectAllQuestions()) {
            System.out.println(q);
        }
    }
    @Test
    void testAnswerMapper() {
        Answer answer = new Answer(
                null,
                1,
                1,
                1,
                LocalDateTime.now(),
                1,
                "[\"option2\"]");
        answerMapper.insertAnswer(answer);
        for (Answer a : answerMapper.selectAllAnswers()) {
            System.out.println(a);
        }
    }
    //endregion


}
