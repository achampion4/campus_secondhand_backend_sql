package com.hitwh.secondhand.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 黑名单实体，对应表 blacklist
 * 负责人：范振扬  日期：6/23
 */
@Data
public class Blacklist {
    private Long id;
    private Long userId;     // 拉黑发起者
    private Long blockedId;  // 被拉黑者
    private LocalDateTime createdAt;

    // 关联展示字段（非数据库列）
    private String blockedNickname; // 被拉黑者昵称
}
