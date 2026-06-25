package com.hitwh.secondhand.service;

import com.hitwh.secondhand.dto.OrderForm;
import com.hitwh.secondhand.entity.Order;

import java.util.List;

/**
 * 订单业务接口
 * 负责人：范振扬  日期：6/21
 */
public interface OrderService {

    /** 下单（校验：商品在售、非自售；下单后锁定商品为已售） */
    Long create(Long buyerId, OrderForm form);

    /** 建议2：卖家在聊天中"确认卖出"给某买家（纯交流模式，无支付/物流），返回成交记录ID */
    Long confirmSell(Long sellerId, Long productId, Long buyerId);

    /** 卖家发货 */
    void ship(Long sellerId, Long orderId);

    /** 买家确认收货 */
    void confirm(Long buyerId, Long orderId);

    /** 取消订单（待发货状态可取消，恢复商品在售） */
    void cancel(Long userId, Long orderId);

    /** 我买到的订单 */
    List<Order> myBought(Long buyerId);

    /** 我卖出的订单 */
    List<Order> mySold(Long sellerId);

    /** 订单详情（含明细） */
    Order detail(Long orderId);
}
