package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.Result;
import com.hitwh.secondhand.entity.Blacklist;
import com.hitwh.secondhand.service.BlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 黑名单接口（均需登录）
 * 负责人：范振扬  日期：6/23
 */
@RestController
@RequestMapping("/api/blacklist")
public class BlacklistController {

    private final BlacklistService blacklistService;

    public BlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    /** 拉黑某人 */
    @PostMapping("/block/{blockedId}")
    public Result<Void> block(@PathVariable Long blockedId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        blacklistService.block(userId, blockedId);
        return Result.success();
    }

    /** 移出黑名单 */
    @DeleteMapping("/{blockedId}")
    public Result<Void> unblock(@PathVariable Long blockedId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        blacklistService.unblock(userId, blockedId);
        return Result.success();
    }

    /** 我的黑名单 */
    @GetMapping("/list")
    public Result<List<Blacklist>> list(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(blacklistService.list(userId));
    }
}
