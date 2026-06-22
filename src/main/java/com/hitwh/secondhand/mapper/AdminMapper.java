package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.Product;
import com.hitwh.secondhand.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 后台管理 / 数据统计 数据访问
 * 负责人：hjh  日期：6/21
 */
@Mapper
public interface AdminMapper {

    // ---- 用户管理 ----
    List<User> listUsers();

    int updateUserStatus(@Param("userId") Long userId, @Param("status") Integer status);

    // ---- 商品管理 ----
    List<Product> listAllProducts();

    // ---- 数据统计 ----
    long countUsers();

    long countProducts();

    long countOrders();

    /** 已完成订单成交总额 */
    BigDecimal totalSales();

    /** 热门分类（按在售商品数倒序） */
    List<Map<String, Object>> hotCategories();

    /** 活跃用户（按下单数倒序 Top5） */
    List<Map<String, Object>> activeUsers();
}
