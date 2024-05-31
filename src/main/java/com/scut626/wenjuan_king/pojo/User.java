package com.scut626.wenjuan_king.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private Integer uid;
    private String username;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime lastLoginTime;
}
