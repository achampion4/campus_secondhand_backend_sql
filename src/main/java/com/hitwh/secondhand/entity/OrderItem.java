package com.hitwh.secondhand.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单明细实体，对应表 order_item（6/21 订单模块使用）
 * 负责人：范振扬  日期：6/19
 */
@Data
public class OrderItem {
    private Long itemId;      // 明细ID
    private Long orderId;     // 订单ID
    private Long productId;   // 商品ID
    private BigDecimal price; // 成交价格

    // 关联展示字段(非数据库列)
    private String productTitle; // 商品标题
}
