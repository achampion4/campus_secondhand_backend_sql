package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.entity.Message;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.MessageMapper;
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

    public MessageServiceImpl(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
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
