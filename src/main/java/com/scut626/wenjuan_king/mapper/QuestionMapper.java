package com.scut626.wenjuan_king.mapper;

import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper

public interface QuestionMapper {

    @Select("select * from question")
    public List<Question> selectAllQuestions();

    @Options(useGeneratedKeys = true, keyProperty = "qid")
    @Insert("insert into question(pid, create_time, question_type, question_title, question_option) VALUES " +
            "                    (#{pid},#{createTime},#{questionType},#{questionTitle},#{questionOption})")
    public int insertQuestion(Question question);

    @Select("select * from question where pid = #{pid}")
    List<Question> selectQuestionsByPid(Integer pid);

}
