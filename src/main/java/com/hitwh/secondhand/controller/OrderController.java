package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.Result;
import com.hitwh.secondhand.dto.OrderForm;
import com.hitwh.secondhand.entity.Order;
import com.hitwh.secondhand.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单接口（均需登录）
 * 负责人：范振扬  日期：6/21
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /** 下单 */
    @PostMapping
    public Result<Long> create(@Valid @RequestBody OrderForm form, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(orderService.create(userId, form));
    }

    /** 我买到的 */
    @GetMapping("/bought")
    public Result<List<Order>> bought(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(orderService.myBought(userId));
    }

    /** 我卖出的 */
    @GetMapping("/sold")
    public Result<List<Order>> sold(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(orderService.mySold(userId));
    }

    /** 订单详情 */
    @GetMapping("/detail/{orderId}")
    public Result<Order> detail(@PathVariable Long orderId) {
        return Result.success(orderService.detail(orderId));
    }

    /** 卖家发货 */
    @PutMapping("/ship/{orderId}")
    public Result<Void> ship(@PathVariable Long orderId, HttpServletRequest request) {
        orderService.ship((Long) request.getAttribute("userId"), orderId);
        return Result.success();
    }

    /** 买家确认收货 */
    @PutMapping("/confirm/{orderId}")
    public Result<Void> confirm(@PathVariable Long orderId, HttpServletRequest request) {
        orderService.confirm((Long) request.getAttribute("userId"), orderId);
        return Result.success();
    }

    /** 取消订单 */
    @PutMapping("/cancel/{orderId}")
    public Result<Void> cancel(@PathVariable Long orderId, HttpServletRequest request) {
        orderService.cancel((Long) request.getAttribute("userId"), orderId);
        return Result.success();
    }
}
