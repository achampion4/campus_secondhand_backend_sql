package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品分类数据访问
 * 负责人：董炜文  日期：6/19
 */
@Mapper
public interface CategoryMapper {

    /** 查询全部分类 */
    List<Category> findAll();
}
