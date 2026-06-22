package com.hitwh.secondhand.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 举报实体，对应表 report
 * 负责人：hjh  日期：6/21
 */
@Data
public class Report {
    private Long reportId;
    private Long reporterId;
    private Long productId;
    private String reason;
    private Integer status; // 0待处理,1已处理,2已驳回
    private LocalDateTime createdAt;

    // 关联展示字段（非数据库列）
    private String productTitle;
    private String reporterNickname;
}
