package com.hitwh.secondhand.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 聊天消息实体，对应表 message
 * 负责人：范振扬  日期：6/21
 */
@Data
public class Message {
    private Long messageId;
    private Long senderId;
    private Long receiverId;
    private Long productId;
    private String content;
    private Integer isRead;
    private LocalDateTime createdAt;

    // 关联展示字段（非数据库列）
    private String senderNickname;
    private String receiverNickname; // 接收者昵称
    private String productTitle;     // 关联商品标题
}
