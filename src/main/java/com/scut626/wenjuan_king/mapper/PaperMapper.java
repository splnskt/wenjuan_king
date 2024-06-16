package com.scut626.wenjuan_king.mapper;

import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaperMapper {

    /**
     * 从 'paper' 表中检索所有问卷。
     *
     * @return 一个包含所有问卷的 Paper 对象列表。
     */
    @Select("select * from paper")
    public List<Paper> selectAllPapers();

    /**
     * 插入一个新的问卷到 'paper' 表中，并使用自动生成的主键。
     *
     * @param paper 要插入的 Paper 对象。
     * @return 插入操作影响的行数。
     */
    @Options(useGeneratedKeys = true, keyProperty = "pid")
    @Insert("insert into paper(uid, title, create_time, status, start_time, end_time,template) " +
            "          VALUES (#{uid},#{title},#{createTime},#{status},#{startTime},#{endTime},#{template})")
    public int insertPaper(Paper paper);

    /**
     * 根据指定的 pid 删除 'paper' 表中的问卷。
     *
     * @param pid 要删除的问卷的 pid。
     * @return 删除操作影响的行数。
     */
    @Delete("delete from paper where pid = #{pid}")
    public int deletePaperById(int pid);

    /**
     * 根据指定的 pid 从 'paper' 表中检索问卷。
     *
     * @param pid 要检索的问卷的 pid。
     * @return 一个包含匹配问卷的 Paper 对象列表。
     */
    @Select("select * from paper where pid = #{pid}")
    List<Paper> selectPapersByPid(Integer pid);

    /**
     * 根据条件检索问卷列表。
     *
     * @param name 问卷名称。
     * @param page 页码。
     * @param pageSize 每页大小。
     * @param uid 用户 ID。
     * @return 一个包含匹配条件的 Paper 对象列表。
     */
    List<Paper> selectPaperList(String name, Integer page, Integer pageSize, Integer uid, Integer template);

    /**
     * 根据条件统计问卷数量。
     *
     * @param name 问卷名称。
     * @param page 页码。
     * @param pageSize 每页大小。
     * @param uid 用户 ID。
     * @return 匹配条件的问卷数量。
     */
    Long paperCount(String name, Integer page, Integer pageSize, Integer uid, Integer template);

    /**
     * 根据指定的 pid 增加问卷的访问次数。
     *
     * @param pid 要增加访问次数的问卷的 pid。
     */
    @Update("update paper set access_count=access_count+1 where pid = #{pid}")
    void addPaperAccessCount(Integer pid);

    @Update("update paper set `like`=`like`+1 where pid = #{pid}")
    void likePaperByPid(Integer pid);
}
