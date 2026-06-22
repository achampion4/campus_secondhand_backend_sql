package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.Result;
import com.hitwh.secondhand.entity.Report;
import com.hitwh.secondhand.service.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * 举报提交接口（用户，需登录）
 * 负责人：hjh  日期：6/21
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /** 举报商品（body: productId, reason） */
    @PostMapping
    public Result<Long> report(@RequestBody Report report, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(reportService.report(userId, report.getProductId(), report.getReason()));
    }
}
