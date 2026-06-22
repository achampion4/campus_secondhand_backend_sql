package com.hitwh.secondhand.dto;

import com.hitwh.secondhand.entity.Product;
import lombok.Data;

import java.util.List;

/**
 * AI 导购推荐结果：一个关键词 + 该关键词命中的商品
 * 负责人：hjh  日期：6/22
 */
@Data
public class AiRecommendGroup {
    private String keyword;          // AI 提取的需求关键词
    private List<Product> products;  // 该关键词命中的在售商品

    public AiRecommendGroup(String keyword, List<Product> products) {
        this.keyword = keyword;
        this.products = products;
    }
}
