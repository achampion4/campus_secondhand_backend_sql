package com.hitwh.secondhand.entity;

import lombok.Data;

/**
 * 商品图片实体，对应表 product_image
 * 负责人：董炜文  日期：6/19
 */
@Data
public class ProductImage {
    private Long imageId;    // 图片ID
    private Long productId;  // 所属商品
    private String url;      // 图片URL
    private Integer sortOrder; // 排序
}
