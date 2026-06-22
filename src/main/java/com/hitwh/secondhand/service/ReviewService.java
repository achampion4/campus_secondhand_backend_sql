package com.hitwh.secondhand.service;

import com.hitwh.secondhand.entity.Review;

import java.util.List;

/**
 * 评价业务接口
 * 负责人：范振扬  日期：6/21
 */
public interface ReviewService {

    /** 评价（仅订单参与者、订单已完成、且未评价过） */
    Long create(Long reviewerId, Long orderId, Integer rating, String content);

    /** 某用户收到的评价 */
    List<Review> listByTarget(Long targetId);
}
