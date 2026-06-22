package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 评价数据访问
 * 负责人：范振扬  日期：6/21
 */
@Mapper
public interface ReviewMapper {

    int insert(Review review);

    /** 判断该订单是否已被某人评价过（防重复评价） */
    int countByOrderAndReviewer(@Param("orderId") Long orderId, @Param("reviewerId") Long reviewerId);

    /** 某用户收到的评价列表（带评价人昵称） */
    java.util.List<Review> findByTarget(Long targetId);
}
