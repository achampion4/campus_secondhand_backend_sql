package com.hitwh.secondhand.service;

import com.hitwh.secondhand.common.PageResult;
import com.hitwh.secondhand.dto.ProductForm;
import com.hitwh.secondhand.dto.ProductQuery;
import com.hitwh.secondhand.entity.Product;

/**
 * 商品业务接口
 * 负责人：董炜文  日期：6/19
 */
public interface ProductService {

    /** 发布商品，返回商品ID */
    Long publish(Long sellerId, ProductForm form);

    /** 编辑商品(仅卖家本人) */
    void update(Long sellerId, ProductForm form);

    /** 商品详情(浏览量+1，带图片) */
    Product detail(Long productId);

    /** 分页查询商品列表 */
    PageResult<Product> page(ProductQuery query);

    /** 下架商品(仅卖家本人) */
    void offShelf(Long sellerId, Long productId);

    /** 我发布的商品(含各状态) */
    java.util.List<Product> mine(Long sellerId);
}
