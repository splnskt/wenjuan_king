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

    /**
     * 从 'question' 表中检索所有问题。
     *
     * @return 一个包含所有问题的 Question 对象列表。
     */
    @Select("select * from question")
    public List<Question> selectAllQuestions();

    /**
     * 插入一个新的问题到 'question' 表中，并使用自动生成的主键。
     *
     * @param question 要插入的 Question 对象。
     * @return 插入操作影响的行数。
     */
    @Options(useGeneratedKeys = true, keyProperty = "qid")
    @Insert("insert into question(pid, create_time, question_type, question_title, question_option) VALUES " +
            "                    (#{pid},#{createTime},#{questionType},#{questionTitle},#{questionOption})")
    public int insertQuestion(Question question);

    /**
     * 根据指定的 pid 从 'question' 表中检索所有相关的问题。
     *
     * @param pid 相关问题的 pid。
     * @return 一个包含所有相关问题的 Question 对象列表。
     */
    @Select("select * from question where pid = #{pid}")
    List<Question> selectQuestionsByPid(Integer pid);
}
