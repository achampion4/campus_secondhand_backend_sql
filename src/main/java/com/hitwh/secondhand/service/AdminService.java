package com.hitwh.secondhand.service;

import com.hitwh.secondhand.entity.Product;
import com.hitwh.secondhand.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 后台管理 / 统计 业务接口
 * 负责人：hjh 日期：6/21
 */
public interface AdminService {

    List<User> listUsers();

    /** 封禁/解封用户（status:0封禁,1正常） */
    void setUserStatus(Long userId, Integer status);

    List<Product> listAllProducts();

    /** 强制下架商品 */
    void forceOffShelf(Long productId);

    /** 平台数据统计汇总 */
    Map<String, Object> statistics();
}
