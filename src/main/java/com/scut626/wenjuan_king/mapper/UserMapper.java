package com.scut626.wenjuan_king.mapper;

import com.scut626.wenjuan_king.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper

public interface UserMapper {

    @Select("select * from user")
    public List<User> selectAllUsers();

    @Insert("insert into user(username, password, create_time, last_login_time) " +
            "          values(#{username}, #{password}, #{createTime}, #{lastLoginTime})")
    public int insertUser(User user);

    @Select("select * from user where username = #{username}")
    List<User> selectUserByName(String username);
}
