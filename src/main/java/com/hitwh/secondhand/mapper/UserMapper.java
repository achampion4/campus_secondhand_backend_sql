package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据访问
 * 负责人：董炜文  日期：6/18
 */
@Mapper
public interface UserMapper {

    /** 按用户名查询(用于登录、注册查重) */
    User findByUsername(String username);

    /** 按ID查询 */
    User findById(Long userId);

    /** 新增用户 */
    int insert(User user);

    /** 更新个人资料(昵称、头像) */
    int updateProfile(User user);
}
