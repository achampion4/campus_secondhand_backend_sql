package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.entity.Message;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.MessageMapper;
import com.hitwh.secondhand.service.BlacklistService;
import com.hitwh.secondhand.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 聊天消息业务实现
 * 负责人：范振扬  日期：6/21
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;
    private final BlacklistService blacklistService;

    public MessageServiceImpl(MessageMapper messageMapper, BlacklistService blacklistService) {
        this.messageMapper = messageMapper;
        this.blacklistService = blacklistService;
    }

    @Override
    public Long send(Long senderId, Message message) {
        if (message.getReceiverId() == null || message.getProductId() == null
                || message.getContent() == null || message.getContent().isBlank()) {
            throw new BusinessException("消息参数不完整");
        }
        if (message.getReceiverId().equals(senderId)) {
            throw new BusinessException("不能给自己发消息");
        }
        // 建议5：被对方拉黑则不能发消息
        if (blacklistService.isBlockedBy(senderId, message.getReceiverId())) {
            throw new BusinessException("对方已将你拉黑，无法发送消息");
        }
        message.setSenderId(senderId);
        messageMapper.insert(message);
        return message.getMessageId();
    }

    @Override
    public List<Message> conversation(Long me, Long other, Long productId) {
        // 进入会话即把对方发来的消息标记为已读
        messageMapper.markRead(me, other, productId);
        return messageMapper.conversation(me, other, productId);
    }

    @Override
    public List<Message> recent(Long userId) {
        return messageMapper.recentInvolving(userId);
    }
}
