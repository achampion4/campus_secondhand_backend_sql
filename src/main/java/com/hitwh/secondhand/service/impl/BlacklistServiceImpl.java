package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.entity.Blacklist;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.BlacklistMapper;
import com.hitwh.secondhand.service.BlacklistService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 黑名单业务实现
 * 负责人：范振扬  日期：6/23
 */
@Service
public class BlacklistServiceImpl implements BlacklistService {

    private final BlacklistMapper blacklistMapper;

    public BlacklistServiceImpl(BlacklistMapper blacklistMapper) {
        this.blacklistMapper = blacklistMapper;
    }

    @Override
    public void block(Long userId, Long blockedId) {
        if (userId.equals(blockedId)) {
            throw new BusinessException("不能拉黑自己");
        }
        // 已拉黑则忽略（唯一约束也会兜底）
        if (blacklistMapper.countBlocked(userId, blockedId) > 0) {
            return;
        }
        Blacklist b = new Blacklist();
        b.setUserId(userId);
        b.setBlockedId(blockedId);
        blacklistMapper.insert(b);
    }

    @Override
    public void unblock(Long userId, Long blockedId) {
        blacklistMapper.delete(userId, blockedId);
    }

    @Override
    public List<Blacklist> list(Long userId) {
        return blacklistMapper.findByUserId(userId);
    }

    @Override
    public boolean isBlockedBy(Long a, Long b) {
        // b 是否拉黑了 a
        return blacklistMapper.countBlocked(b, a) > 0;
    }
}
