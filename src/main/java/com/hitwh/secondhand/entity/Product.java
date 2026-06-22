package com.hitwh.secondhand.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品实体，对应表 product
 * 负责人：董炜文  日期：6/19
 */
@Data
public class Product {
    private Long productId;        // 商品ID
    private Long sellerId;         // 卖家ID
    private Long categoryId;       // 分类ID
    private String title;          // 标题
    private String description;    // 描述
    private BigDecimal price;      // 价格
    private Integer conditionLevel; // 成色:1全新,2几乎全新,3轻微使用,4明显使用
    private Integer status;        // 0下架,1在售,2已售
    private Integer viewCount;     // 浏览量
    private LocalDateTime createdAt; // 发布时间

    // 以下为关联展示字段(非数据库列，查询时按需填充)
    private String categoryName;   // 分类名
    private String sellerNickname; // 卖家昵称
    private List<String> images;   // 图片URL列表(详情用)
    private String cover;          // 封面图(列表用，取第一张)
}
