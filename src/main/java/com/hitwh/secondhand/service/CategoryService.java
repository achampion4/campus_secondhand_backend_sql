package com.hitwh.secondhand.service;

import com.hitwh.secondhand.entity.Category;

import java.util.List;

/**
 * 商品分类业务接口
 * 负责人：董炜文  日期：6/19
 */
public interface CategoryService {

    /** 查询全部分类 */
    List<Category> listAll();
}
