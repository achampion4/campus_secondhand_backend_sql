package com.hitwh.secondhand.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体，对应表 user
 * 负责人：董炜文  日期：6/18
 */
@Data
public class User {
    private Long userId;          // 用户ID
    private String username;      // 登录账号
    private String password;      // 密码(BCrypt)
    private String nickname;      // 昵称
    private String avatar;        // 头像URL
    private Integer creditScore;  // 信用分
    private Integer role;         // 0普通用户,1管理员
    private Integer status;       // 0封禁,1正常
    private LocalDateTime createdAt; // 注册时间
}
