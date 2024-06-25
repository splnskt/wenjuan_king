package com.scut626.wenjuan_king.service.impl;

// 导入必要的类和接口

import com.scut626.wenjuan_king.mapper.UserMapper;
import com.scut626.wenjuan_king.pojo.User;
import com.scut626.wenjuan_king.service.UserService;
import com.scut626.wenjuan_king.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// 表示这是一个服务类，实现了 UserService 接口
@Service
public class UserServiceImpl implements UserService {

    // 自动注入 UserMapper
    @Autowired
    UserMapper userMapper;

    // 用户注册方法
    @Override
    public boolean register(User user) {
        // 更新用户注册时间和最后登录时间为当前时间
        user.setCreateTime(LocalDateTime.now());
        user.setLastLoginTime(LocalDateTime.now());

        // 对用户密码进行 MD5 加密
        user.setPassword(MD5Util.md5(user.getPassword()));

        // 将用户信息插入数据库
        int rows = userMapper.insertUser(user);

        // 返回插入结果，非零表示插入成功
        return rows != 0;
    }

    // 用户登录方法

    /**
     * 登陆操作
     *
     * @param user 登录的用户信息
     * @return 返回一个数字，
     * 0： 登录成功
     * 1： 用户名不存在
     * 2： 密码错误
     * 3:  用户被封禁
     * 4：  用户是管理员
     */
    @Override
    public int login(User user) {
        // 根据用户名获取用户信息
        List<User> userByName = userMapper.selectUserByName(user.getUsername());

        // 如果用户不存在，返回 1
        if (userByName == null || userByName.size() == 0) {
            return 1;
        }

        // 将要登录的用户密码加密，并与数据库中的用户密码比对
        if (MD5Util.md5(user.getPassword()).equals(userByName.get(0).getPassword())) {
            // 密码正确，获取该用户的所有信息
            user = userByName.get(0);
            if (user.getBan() == 1) {
                //用户被封禁
                return 3;
            } else if (user.getAdmin() == 1) {
                //用户是管理员
                return 4;
            }
            return 0;
        } else {
            // 密码错误，返回 2
            return 2;
        }
    }

    @Override
    public void updateImageUriByUid(String originalFilename, Integer uid) {
        userMapper.updateImageUriByUid(originalFilename, uid);
    }

    @Override
    public String selectImageUriByUid(Integer uid) {
        List<String> uri = userMapper.selectImageUriByUid(uid);
        if (uri == null || uri.isEmpty()) {
            return null;
        } else {
            return uri.get(0);
        }
    }

    @Override
    public List<User> userList(String name, Integer page, Integer pageSize) {
        if (page != null) {
            page = (page - 1) * pageSize;
        }
        List<User> list = userMapper.selectUsersByName(name, page, pageSize);
        return list;
    }

    @Override
    public void deleteUser(Integer uid) {
        userMapper.deleteUser(uid);
    }

    @Override
    public void setUserBanned(Integer uid, int i) {
        userMapper.setUserBanned(uid, i);
    }

    @Override
    public Long getUserCount(String name) {
        return userMapper.userCount(name);
    }
}
