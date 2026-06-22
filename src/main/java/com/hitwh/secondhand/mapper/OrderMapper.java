package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单数据访问（6/21 订单模块使用）
 * 负责人：范振扬  日期：6/20
 */
@Mapper
public interface OrderMapper {

    /** 新增订单 */
    int insert(Order order);

    /** 按ID查询订单(含明细) */
    Order findById(Long orderId);

    /** 查询我买到的订单 */
    List<Order> findByBuyer(Long buyerId);

    /** 查询我卖出的订单 */
    List<Order> findBySeller(Long sellerId);

    /** 更新订单状态 */
    int updateStatus(@Param("orderId") Long orderId, @Param("status") Integer status);
}
