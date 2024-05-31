package com.scut626.wenjuan_king.mapper;

import com.scut626.wenjuan_king.pojo.Paper;
import com.scut626.wenjuan_king.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper

public interface PaperMapper {

    @Select("select * from paper")
    public List<Paper> selectAllPapers();

    @Insert("insert into paper(uid, title, create_time, status, start_time, end_time) " +
            "          VALUES (#{uid},#{title},#{createTime},#{status},#{startTime},#{endTime})")
    public int insertPaper(Paper paper);
}
