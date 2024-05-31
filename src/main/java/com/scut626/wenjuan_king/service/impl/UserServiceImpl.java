package com.scut626.wenjuan_king.service.impl;

import com.scut626.wenjuan_king.mapper.UserMapper;
import com.scut626.wenjuan_king.pojo.User;
import com.scut626.wenjuan_king.service.UserService;
import com.scut626.wenjuan_king.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public boolean register(User user) {
        //更新user注册时间
        user.setCreateTime(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());
        //对用户密码进行MD5加密
        user.setPassword(MD5Util.md5(user.getPassword()));
        //插入数据库
        int rows = userMapper.insertUser(user);
        return rows != 0;
    }

    @Override
    public int login(User user) {
        //根据用户名获取用户信息
        List<User> userByName = userMapper.selectUserByName(user.getUsername());
        if(userByName == null || userByName.size() == 0)
        {
            //该用户不存在
            return 1;
        }
        //将要登录的用户密码加密，并与获取到的用户的密码比对
        if( MD5Util.md5(user.getPassword())
                .equals(userByName.get(0).getPassword()) )
        {
            //密码正确
            return 0;
        }
        else{
            //密码错误
            return 2;
        }
    }
}
