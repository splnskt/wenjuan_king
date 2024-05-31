package com.scut626.wenjuan_king.mapper;

import com.scut626.wenjuan_king.pojo.Answer;
import com.scut626.wenjuan_king.pojo.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper

public interface AnswerMapper {

    @Select("select * from answer")
    public List<Answer> selectAllAnswers();

    @Insert("insert into answer(pid, qid, create_time, question_type, answer_content) VALUES " +
            "                    (#{pid},#{qid},#{createTime},#{questionType},#{answerContent})")
    public int insertAnswer(Answer answer);
}
