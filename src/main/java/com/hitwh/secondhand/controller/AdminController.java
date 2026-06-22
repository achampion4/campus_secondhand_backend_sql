package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.Result;
import com.hitwh.secondhand.common.ResultCode;
import com.hitwh.secondhand.entity.Product;
import com.hitwh.secondhand.entity.Report;
import com.hitwh.secondhand.entity.User;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.service.AdminService;
import com.hitwh.secondhand.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 后台管理接口（仅管理员 role=1）
 * 负责人：hjh  日期：6/21
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final ReportService reportService;

    public AdminController(AdminService adminService, ReportService reportService) {
        this.adminService = adminService;
        this.reportService = reportService;
    }

    /** 管理员权限校验：非管理员抛 403 */
    private void requireAdmin(HttpServletRequest request) {
        Object role = request.getAttribute("role");
        int r = (role == null) ? 0 : ((Number) role).intValue();
        if (r != 1) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }
    }

    // ---- 用户管理 ----
    @GetMapping("/users")
    public Result<List<User>> users(HttpServletRequest request) {
        requireAdmin(request);
        return Result.success(adminService.listUsers());
    }

    @PutMapping("/user/{userId}/status")
    public Result<Void> setUserStatus(@PathVariable Long userId, @RequestParam Integer status,
                                      HttpServletRequest request) {
        requireAdmin(request);
        adminService.setUserStatus(userId, status);
        return Result.success();
    }

    // ---- 商品管理 ----
    @GetMapping("/products")
    public Result<List<Product>> products(HttpServletRequest request) {
        requireAdmin(request);
        return Result.success(adminService.listAllProducts());
    }

    @PutMapping("/product/offShelf/{productId}")
    public Result<Void> offShelf(@PathVariable Long productId, HttpServletRequest request) {
        requireAdmin(request);
        adminService.forceOffShelf(productId);
        return Result.success();
    }

    // ---- 举报处理 ----
    @GetMapping("/reports")
    public Result<List<Report>> reports(HttpServletRequest request) {
        requireAdmin(request);
        return Result.success(reportService.listAll());
    }

    @PutMapping("/report/{reportId}")
    public Result<Void> processReport(@PathVariable Long reportId, @RequestParam Integer status,
                                      HttpServletRequest request) {
        requireAdmin(request);
        reportService.process(reportId, status);
        return Result.success();
    }

    // ---- 数据统计 ----
    @GetMapping("/stats")
    public Result<Map<String, Object>> stats(HttpServletRequest request) {
        requireAdmin(request);
        return Result.success(adminService.statistics());
    }
}
