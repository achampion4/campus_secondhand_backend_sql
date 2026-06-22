package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.Result;
import com.hitwh.secondhand.entity.Review;
import com.hitwh.secondhand.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评价接口（均需登录）
 * 负责人：范振扬  日期：6/21
 */
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /** 提交评价（body: orderId, rating, content） */
    @PostMapping
    public Result<Long> create(@RequestBody Review review, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(reviewService.create(userId, review.getOrderId(), review.getRating(), review.getContent()));
    }

    /** 查询某用户收到的评价 */
    @GetMapping("/user/{targetId}")
    public Result<List<Review>> listByTarget(@PathVariable Long targetId) {
        return Result.success(reviewService.listByTarget(targetId));
    }
}
