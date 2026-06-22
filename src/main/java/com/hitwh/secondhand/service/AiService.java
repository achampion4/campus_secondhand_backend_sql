package com.hitwh.secondhand.service;

import com.hitwh.secondhand.dto.AiRecommendGroup;

import java.util.List;

/**
 * AI 智能导购业务接口
 * 负责人：hjh  日期：6/22
 */
public interface AiService {

    /**
     * 根据用户自然语言需求推荐商品
     * @param need 用户需求描述（如"我搬家了，需要照明的东西"）
     * @return 按关键词分组的推荐商品
     */
    List<AiRecommendGroup> recommend(String need);
}
