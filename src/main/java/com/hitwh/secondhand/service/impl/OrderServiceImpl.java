package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.dto.OrderForm;
import com.hitwh.secondhand.entity.Order;
import com.hitwh.secondhand.entity.OrderItem;
import com.hitwh.secondhand.entity.Product;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.OrderItemMapper;
import com.hitwh.secondhand.mapper.OrderMapper;
import com.hitwh.secondhand.mapper.ProductMapper;
import com.hitwh.secondhand.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 订单业务实现
 * 订单状态：1待发货 2待收货 3已完成 4已取消
 * 负责人：范振扬  日期：6/21
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductMapper productMapper;

    public OrderServiceImpl(OrderMapper orderMapper, OrderItemMapper orderItemMapper, ProductMapper productMapper) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public Long create(Long buyerId, OrderForm form) {
        Product product = productMapper.findById(form.getProductId());
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        // 完整性约束校验
        if (product.getStatus() != 1) {
            throw new BusinessException("商品已售出或已下架");
        }
        if (product.getSellerId().equals(buyerId)) {
            throw new BusinessException("不能购买自己发布的商品");
        }
        // 创建订单
        Order order = new Order();
        order.setBuyerId(buyerId);
        order.setSellerId(product.getSellerId());
        order.setTotalAmount(product.getPrice());
        order.setStatus(1); // 待发货
        order.setAddressId(form.getAddressId());
        orderMapper.insert(order);
        // 订单明细
        OrderItem item = new OrderItem();
        item.setOrderId(order.getOrderId());
        item.setProductId(product.getProductId());
        item.setPrice(product.getPrice());
        orderItemMapper.batchInsert(Collections.singletonList(item));
        // 锁定商品为已售，防止重复购买
        productMapper.updateStatus(product.getProductId(), 2);
        return order.getOrderId();
    }

    @Override
    public void ship(Long sellerId, Long orderId) {
        Order order = getOwnedOrder(orderId, sellerId, false);
        if (order.getStatus() != 1) {
            throw new BusinessException("订单当前状态不可发货");
        }
        orderMapper.updateStatus(orderId, 2); // 待收货
    }

    @Override
    public void confirm(Long buyerId, Long orderId) {
        Order order = getOwnedOrder(orderId, buyerId, true);
        if (order.getStatus() != 2) {
            throw new BusinessException("订单当前状态不可确认收货");
        }
        orderMapper.updateStatus(orderId, 3); // 已完成
    }

    @Override
    @Transactional
    public void cancel(Long userId, Long orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (!order.getBuyerId().equals(userId) && !order.getSellerId().equals(userId)) {
            throw new BusinessException("无权操作该订单");
        }
        if (order.getStatus() != 1) {
            throw new BusinessException("仅待发货订单可取消");
        }
        orderMapper.updateStatus(orderId, 4); // 已取消
        // 恢复商品为在售
        List<OrderItem> items = orderItemMapper.findByOrderId(orderId);
        for (OrderItem it : items) {
            productMapper.updateStatus(it.getProductId(), 1);
        }
    }

    @Override
    public List<Order> myBought(Long buyerId) {
        List<Order> orders = orderMapper.findByBuyer(buyerId);
        fillItems(orders);
        return orders;
    }

    @Override
    public List<Order> mySold(Long sellerId) {
        List<Order> orders = orderMapper.findBySeller(sellerId);
        fillItems(orders);
        return orders;
    }

    @Override
    public Order detail(Long orderId) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        order.setItems(orderItemMapper.findByOrderId(orderId));
        return order;
    }

    /** 校验订单归属：buyer=true 校验买家，false 校验卖家 */
    private Order getOwnedOrder(Long orderId, Long userId, boolean buyer) {
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        Long ownerId = buyer ? order.getBuyerId() : order.getSellerId();
        if (!ownerId.equals(userId)) {
            throw new BusinessException("无权操作该订单");
        }
        return order;
    }

    /** 为订单列表填充明细 */
    private void fillItems(List<Order> orders) {
        for (Order o : orders) {
            o.setItems(orderItemMapper.findByOrderId(o.getOrderId()));
        }
    }
}
