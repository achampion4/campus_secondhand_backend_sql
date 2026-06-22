package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 举报数据访问
 * 负责人：hjh  日期：6/21
 */
@Mapper
public interface ReportMapper {

    int insert(Report report);

    /** 全部举报（带商品标题、举报人昵称），供后台处理 */
    List<Report> findAll();

    /** 按ID查询(处理举报时取被举报商品ID) */
    Report findById(Long reportId);

    int updateStatus(@Param("reportId") Long reportId, @Param("status") Integer status);
}
