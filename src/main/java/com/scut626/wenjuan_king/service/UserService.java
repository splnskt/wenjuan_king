package com.scut626.wenjuan_king.service;

import com.scut626.wenjuan_king.pojo.User;

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
     */
    int login(User user);
}

