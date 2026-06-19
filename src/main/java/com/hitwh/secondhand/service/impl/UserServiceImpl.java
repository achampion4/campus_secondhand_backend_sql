package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.dto.LoginDTO;
import com.hitwh.secondhand.dto.RegisterDTO;
import com.hitwh.secondhand.entity.User;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.UserMapper;
import com.hitwh.secondhand.service.UserService;
import com.hitwh.secondhand.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户业务实现
 * 负责人：董炜文  日期：6/18
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    // BCrypt 密码加密器
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserMapper userMapper, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Long register(RegisterDTO dto) {
        // 用户名查重
        if (userMapper.findByUsername(dto.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // 加密存储
        user.setNickname(dto.getNickname());
        userMapper.insert(user);
        return user.getUserId();
    }

    @Override
    public Map<String, Object> login(LoginDTO dto) {
        User user = userMapper.findByUsername(dto.getUsername());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        // 校验密码
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        if (user.getStatus() != null && user.getStatus() == 0) {
            throw new BusinessException("账号已被封禁");
        }
        // 生成 Token
        String token = jwtUtil.generateToken(user.getUserId(), user.getRole());
        user.setPassword(null); // 返回前抹掉密码
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);
        return result;
    }

    @Override
    public User getProfile(Long userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public void updateProfile(Long userId, User user) {
        user.setUserId(userId);
        userMapper.updateProfile(user);
    }
}
