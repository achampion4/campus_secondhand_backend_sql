package com.hitwh.secondhand.common;

import lombok.Data;

import java.util.List;

/**
 * 分页结果封装
 * 负责人：董炜文  日期：6/19
 *
 * @param <T> 列表元素类型
 */
@Data
public class PageResult<T> {
    private long total;       // 总记录数
    private int page;         // 当前页
    private int size;         // 每页条数
    private List<T> records;  // 当前页数据

    public PageResult(long total, int page, int size, List<T> records) {
        this.total = total;
        this.page = page;
        this.size = size;
        this.records = records;
    }
}
