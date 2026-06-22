package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.Result;
import com.hitwh.secondhand.entity.Favorite;
import com.hitwh.secondhand.service.FavoriteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏接口（均需登录）
 * 负责人：范振扬  日期：6/21
 */
@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /** 切换收藏，返回切换后是否已收藏 */
    @PostMapping("/toggle/{productId}")
    public Result<Boolean> toggle(@PathVariable Long productId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.toggle(userId, productId));
    }

    /** 我的收藏列表 */
    @GetMapping("/list")
    public Result<List<Favorite>> list(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.list(userId));
    }

    /** 是否已收藏某商品 */
    @GetMapping("/check/{productId}")
    public Result<Boolean> check(@PathVariable Long productId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(favoriteService.isFavorited(userId, productId));
    }
}
