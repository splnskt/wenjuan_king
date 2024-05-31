package com.scut626.wenjuan_king.mapper;

import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper

public interface QuestionMapper {

    @Select("select * from question")
    public List<Question> selectAllQuestions();

    @Insert("insert into question(pid, create_time, question_type, question_title, question_option) VALUES " +
            "                    (#{pid},#{createTime},#{questionType},#{questionTitle},#{questionOption})")
    public int insertQuestion(Question question);
}
