package com.hitwh.secondhand.service;

import com.hitwh.secondhand.entity.Blacklist;

import java.util.List;

/**
 * 黑名单业务接口
 * 负责人：范振扬  日期：6/23
 */
public interface BlacklistService {

    /** 拉黑某人 */
    void block(Long userId, Long blockedId);

    /** 移出黑名单 */
    void unblock(Long userId, Long blockedId);

    /** 我的黑名单列表 */
    List<Blacklist> list(Long userId);

    /** a 是否被 b 拉黑（用于发消息前校验） */
    boolean isBlockedBy(Long a, Long b);
}
