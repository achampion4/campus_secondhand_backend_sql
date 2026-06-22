package com.hitwh.secondhand.dto;

import com.hitwh.secondhand.entity.Product;
import lombok.Data;

/**
 * AI 导购推荐项：一件被 AI 选中的商品 + 推荐理由
 * 负责人：hjh  日期：6/22
 */
@Data
public class AiRecommendGroup {
    private Product product;  // 推荐的商品
    private String reason;    // AI 给出的推荐理由

    public AiRecommendGroup(Product product, String reason) {
        this.product = product;
        this.reason = reason;
    }
}
