package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.ProductImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品图片数据访问
 * 负责人：董炜文  日期：6/19
 */
@Mapper
public interface ProductImageMapper {

    /** 批量新增图片 */
    int batchInsert(List<ProductImage> images);

    /** 查询某商品的图片URL列表 */
    List<String> findUrlsByProductId(Long productId);

    /** 删除某商品的全部图片(编辑时先清后插) */
    int deleteByProductId(Long productId);
}
