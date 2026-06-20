package com.hitwh.secondhand.dto;

import lombok.Data;

/**
 * 商品列表查询条件
 * 负责人：董炜文  日期：6/19
 */
@Data
public class ProductQuery {
    private String keyword;    // 关键词(标题模糊匹配)
    private Long categoryId;   // 分类筛选
    private Integer page = 1;  // 页码，默认第1页
    private Integer size = 12; // 每页条数，默认12

    /** 计算 SQL 偏移量 */
    public int getOffset() {
        return (page - 1) * size;
    }
}
