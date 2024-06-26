package com.scut626.wenjuan_king.mapper;

import com.scut626.wenjuan_king.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 从 'user' 表中检索所有用户。
     *
     * @return 一个包含所有用户的 User 对象列表。
     */
    @Select("SELECT * FROM user")
    public List<User> selectAllUsers();

    /**
     * 向 'user' 表中插入一个新用户。
     *
     * @param user 包含要插入的用户详细信息的 User 对象。
     * @return 受插入操作影响的行数。
     */
    @Insert("INSERT INTO user(username, password, create_time, last_login_time) " +
            "VALUES(#{username}, #{password}, #{createTime}, #{lastLoginTime})")
    public int insertUser(User user);

    /**
     * 根据用户名从 'user' 表中检索用户。
     *
     * @param username 要搜索的用户名。
     * @return 一个包含匹配给定用户名的 User 对象列表。
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    List<User> selectUserByName(String username);

    @Update("UPDATE user set image_uri = #{originalFilename} where uid = #{uid}")
    void updateImageUriByUid(String originalFilename, Integer uid);

    @Select("select image_uri from user where uid = #{uid}")
    List<String> selectImageUriByUid(Integer uid);

    List<User> selectUsersByName(String name, Integer page, Integer pageSize);

    @Delete("delete from user where uid = #{uid}")
    void deleteUser(Integer uid);

    @Update("update user set ban = #{i} where uid = #{uid}")
    void setUserBanned(Integer uid, int i);


    Long userCount(String name);
}
