package com.hitwh.secondhand.service;

import com.hitwh.secondhand.entity.Message;

import java.util.List;

/**
 * 聊天消息业务接口
 * 负责人：范振扬  日期：6/21
 */
public interface MessageService {

    /** 发送消息 */
    Long send(Long senderId, Message message);

    /** 获取与某人针对某商品的会话（并把对方发来的标记已读） */
    List<Message> conversation(Long me, Long other, Long productId);

    /** 与我相关的最近消息（会话列表数据源） */
    List<Message> recent(Long userId);
}
