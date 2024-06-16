package com.scut626.wenjuan_king.mapper;

import com.scut626.wenjuan_king.pojo.Answer;
import com.scut626.wenjuan_king.pojo.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper // Indicates that this interface is a MyBatis mapper.
public interface AnswerMapper {

    /**
     * 从 'answer' 表中检索所有回答。
     *
     * @return 一个包含所有回答的 Answer 对象列表。
     */
    @Select("select * from answer")
    public List<Answer> selectAllAnswers();

    /**
     * 根据 'pid' 检索回答。
     *
     * @param pid 要检索回答的 pid。
     * @return 一个包含特定 pid 下回答的 Answer 对象列表。
     */
    @Select("SELECT * FROM answer WHERE pid = #{pid}")
    List<Answer> selectAnswersByPid(int pid);
    @Select("SELECT * FROM answer WHERE qid = #{qid}")
    List<Answer> selectAnswersByQid(int qid);

    /**
     * 将新回答插入到 'answer' 表中。
     *
     * @param answer 要插入的 Answer 对象。
     * @return 表示插入是否成功的整数。
     */
    @Insert("insert into answer(pid, qid, uid, create_time, question_type, answer_content) VALUES " +
            "(#{pid},#{qid},#{uid},#{createTime},#{questionType},#{answerContent})")
    public int insertAnswer(Answer answer);
}
