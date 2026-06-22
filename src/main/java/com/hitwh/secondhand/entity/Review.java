package com.hitwh.secondhand.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评价实体，对应表 review
 * 负责人：范振扬  日期：6/21
 */
@Data
public class Review {
    private Long reviewId;
    private Long orderId;
    private Long reviewerId;
    private Long targetId;
    private Integer rating;
    private String content;
    private LocalDateTime createdAt;

    // 关联展示字段（非数据库列）
    private String reviewerNickname;
}
