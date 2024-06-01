package com.scut626.wenjuan_king.mapper;

import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper

public interface PaperMapper {

    @Select("select * from paper")
    public List<Paper> selectAllPapers();

    @Options(useGeneratedKeys = true, keyProperty = "pid")
    @Insert("insert into paper(uid, title, create_time, status, start_time, end_time) " +
            "          VALUES (#{uid},#{title},#{createTime},#{status},#{startTime},#{endTime})")
    public int insertPaper(Paper paper);

    @Delete("delete from paper where pid = #{pid}")
    public int deletePaperById(int pid);
}



