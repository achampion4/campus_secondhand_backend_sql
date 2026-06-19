package com.hitwh.secondhand.service;

import com.hitwh.secondhand.dto.LoginDTO;
import com.hitwh.secondhand.dto.RegisterDTO;
import com.hitwh.secondhand.entity.User;

import java.util.Map;

/**
 * 用户业务接口
 * 负责人：董炜文  日期：6/18
 */
public interface UserService {

    /** 注册，返回新用户ID */
    Long register(RegisterDTO dto);

    /** 登录，返回 {token, user} */
    Map<String, Object> login(LoginDTO dto);

    /** 查询个人资料(不含密码) */
    User getProfile(Long userId);

    /** 更新个人资料 */
    void updateProfile(Long userId, User user);
}
