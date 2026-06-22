package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单明细数据访问（6/21 订单模块使用）
 * 负责人：范振扬  日期：6/20
 */
@Mapper
public interface OrderItemMapper {

    /** 批量新增订单明细 */
    int batchInsert(List<OrderItem> items);

    /** 查询某订单的明细(含商品标题) */
    List<OrderItem> findByOrderId(Long orderId);
}
