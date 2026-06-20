package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.entity.Category;
import com.hitwh.secondhand.mapper.CategoryMapper;
import com.hitwh.secondhand.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类业务实现
 * 负责人：董炜文  日期：6/19
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<Category> listAll() {
        return categoryMapper.findAll();
    }
}
