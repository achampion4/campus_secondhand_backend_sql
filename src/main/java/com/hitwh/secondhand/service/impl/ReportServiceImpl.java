package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.entity.Report;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.ProductMapper;
import com.hitwh.secondhand.mapper.ReportMapper;
import com.hitwh.secondhand.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 举报业务实现
 * 负责人：hjh  日期：6/21（6/22 处理举报时下架违规商品）
 */
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;
    private final ProductMapper productMapper;

    public ReportServiceImpl(ReportMapper reportMapper, ProductMapper productMapper) {
        this.reportMapper = reportMapper;
        this.productMapper = productMapper;
    }

    @Override
    public Long report(Long reporterId, Long productId, String reason) {
        if (reason == null || reason.isBlank()) {
            throw new BusinessException("请填写举报原因");
        }
        Report report = new Report();
        report.setReporterId(reporterId);
        report.setProductId(productId);
        report.setReason(reason);
        reportMapper.insert(report);
        return report.getReportId();
    }

    @Override
    public List<Report> listAll() {
        return reportMapper.findAll();
    }

    @Override
    @Transactional
    public void process(Long reportId, Integer status) {
        if (status == null || (status != 1 && status != 2)) {
            throw new BusinessException("处理状态非法");
        }
        reportMapper.updateStatus(reportId, status);
        // 处理(确认违规)时，自动下架被举报商品
        if (status == 1) {
            Report report = reportMapper.findById(reportId);
            if (report != null) {
                productMapper.updateStatus(report.getProductId(), 0); // 0=下架
            }
        }
    }
}
