package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.Blacklist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 黑名单数据访问
 * 负责人：范振扬  日期：6/23
 */
@Mapper
public interface BlacklistMapper {

    int insert(Blacklist blacklist);

    int delete(@Param("userId") Long userId, @Param("blockedId") Long blockedId);

    /** 我的黑名单（带被拉黑者昵称） */
    List<Blacklist> findByUserId(Long userId);

    /** 统计 a 是否拉黑了 b（>0 表示已拉黑） */
    int countBlocked(@Param("userId") Long userId, @Param("blockedId") Long blockedId);
}
