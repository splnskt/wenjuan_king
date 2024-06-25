package com.scut626.wenjuan_king.service;

import com.scut626.wenjuan_king.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 注册操作
     * @param user 要注册的用户信息
     * @return 注册是否成功，返回 true 表示成功，false 表示失败
     */
    boolean register(User user);

    /**
     * 登陆操作
     * @param user 登录的用户信息
     * @return 返回一个数字，
     *              0： 登录成功
     *              1： 用户名不存在
     *              2： 密码错误
     *              3:  用户被封禁
     *              4：  用户是管理员
     */
    int login(User user);

    void updateImageUriByUid(String originalFilename, Integer uid);
    String selectImageUriByUid(Integer uid);

    List<User> userList(String name, Integer page, Integer pageSize);

    void deleteUser(Integer uid);

    void setUserBanned(Integer uid, int i);

    Long getUserCount(String name);
}

