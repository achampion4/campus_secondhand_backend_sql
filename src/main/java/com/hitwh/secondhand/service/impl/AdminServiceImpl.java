package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.entity.Product;
import com.hitwh.secondhand.entity.User;
import com.hitwh.secondhand.mapper.AdminMapper;
import com.hitwh.secondhand.mapper.ProductMapper;
import com.hitwh.secondhand.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台管理 / 统计 业务实现
 * 负责人：hjh  日期：6/21
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    private final ProductMapper productMapper;

    public AdminServiceImpl(AdminMapper adminMapper, ProductMapper productMapper) {
        this.adminMapper = adminMapper;
        this.productMapper = productMapper;
    }

    @Override
    public List<User> listUsers() {
        return adminMapper.listUsers();
    }

    @Override
    public void setUserStatus(Long userId, Integer status) {
        adminMapper.updateUserStatus(userId, status);
    }

    @Override
    public List<Product> listAllProducts() {
        return adminMapper.listAllProducts();
    }

    @Override
    public void forceOffShelf(Long productId) {
        productMapper.updateStatus(productId, 0); // 0=下架
    }

    @Override
    public Map<String, Object> statistics() {
        Map<String, Object> stat = new HashMap<>();
        stat.put("userCount", adminMapper.countUsers());
        stat.put("productCount", adminMapper.countProducts());
        stat.put("orderCount", adminMapper.countOrders());
        stat.put("totalSales", adminMapper.totalSales());
        stat.put("hotCategories", adminMapper.hotCategories());
        stat.put("activeUsers", adminMapper.activeUsers());
        return stat;
    }
}
