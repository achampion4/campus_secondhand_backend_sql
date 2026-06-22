package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.entity.Report;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.ReportMapper;
import com.hitwh.secondhand.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 举报业务实现
 * 负责人：hjh  日期：6/21
 */
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;

    public ReportServiceImpl(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
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
    public void process(Long reportId, Integer status) {
        if (status == null || (status != 1 && status != 2)) {
            throw new BusinessException("处理状态非法");
        }
        reportMapper.updateStatus(reportId, status);
    }
}
