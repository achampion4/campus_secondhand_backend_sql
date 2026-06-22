package com.hitwh.secondhand.service;

import com.hitwh.secondhand.entity.Report;

import java.util.List;

/**
 * 举报业务接口
 * 负责人：hjh  日期：6/21
 */
public interface ReportService {

    /** 用户举报商品 */
    Long report(Long reporterId, Long productId, String reason);

    /** 全部举报（后台） */
    List<Report> listAll();

    /** 处理举报（1已处理 / 2已驳回） */
    void process(Long reportId, Integer status);
}
