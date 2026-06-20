package com.hitwh.secondhand.entity;

import lombok.Data;

/**
 * 商品分类实体，对应表 category
 * 负责人：董炜文  日期：6/19
 */
@Data
public class Category {
    private Long categoryId;  // 分类ID
    private String name;      // 分类名称
    private Long parentId;    // 父分类ID,0为顶级
}
