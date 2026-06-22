package com.hitwh.secondhand.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 收藏实体，对应表 favorite
 * 负责人：范振扬  日期：6/21
 */
@Data
public class Favorite {
    private Long favoriteId;
    private Long userId;
    private Long productId;
    private LocalDateTime createdAt;

    // 关联展示字段（非数据库列）
    private String productTitle;
    private BigDecimal productPrice;
    private Integer productStatus;
}
