package com.hitwh.secondhand.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单实体，对应表 order_info（6/21 订单模块使用）
 * 负责人：范振扬  日期：6/19
 */
@Data
public class Order {
    private Long orderId;        // 订单ID
    private Long buyerId;        // 买家ID
    private Long sellerId;       // 卖家ID
    private BigDecimal totalAmount; // 订单总额
    private Integer status;      // 0待付款,1待发货,2待收货,3已完成,4已取消
    private Long addressId;      // 收货地址ID
    private LocalDateTime createdAt; // 下单时间

    // 关联展示字段(非数据库列)
    private List<OrderItem> items; // 订单明细
}
