package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.entity.Order;
import com.hitwh.secondhand.entity.Review;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.OrderMapper;
import com.hitwh.secondhand.mapper.ReviewMapper;
import com.hitwh.secondhand.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评价业务实现
 * 负责人：范振扬  日期：6/21
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final OrderMapper orderMapper;

    public ReviewServiceImpl(ReviewMapper reviewMapper, OrderMapper orderMapper) {
        this.reviewMapper = reviewMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public Long create(Long reviewerId, Long orderId, Integer rating, String content) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new BusinessException("评分需为1-5");
        }
        Order order = orderMapper.findById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        // 必须是订单参与者
        boolean isBuyer = order.getBuyerId().equals(reviewerId);
        boolean isSeller = order.getSellerId().equals(reviewerId);
        if (!isBuyer && !isSeller) {
            throw new BusinessException("无权评价该订单");
        }
        // 订单需已完成才能评价
        if (order.getStatus() != 3) {
            throw new BusinessException("订单完成后才能评价");
        }
        // 不能重复评价
        if (reviewMapper.countByOrderAndReviewer(orderId, reviewerId) > 0) {
            throw new BusinessException("您已评价过该订单");
        }
        Review review = new Review();
        review.setOrderId(orderId);
        review.setReviewerId(reviewerId);
        // 评价对象为交易对方
        review.setTargetId(isBuyer ? order.getSellerId() : order.getBuyerId());
        review.setRating(rating);
        review.setContent(content);
        reviewMapper.insert(review);
        return review.getReviewId();
    }

    @Override
    public List<Review> listByTarget(Long targetId) {
        return reviewMapper.findByTarget(targetId);
    }
}
