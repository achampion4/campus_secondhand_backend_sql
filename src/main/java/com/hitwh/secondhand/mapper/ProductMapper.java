package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.dto.ProductQuery;
import com.hitwh.secondhand.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品数据访问
 * 负责人：董炜文  日期：6/19
 */
@Mapper
public interface ProductMapper {

    /** 新增商品 */
    int insert(Product product);

    /** 更新商品 */
    int update(Product product);

    /** 按ID查询(含分类名、卖家昵称) */
    Product findById(Long productId);

    /** 条件分页查询 */
    List<Product> selectByQuery(ProductQuery query);

    /** 条件统计总数 */
    long countByQuery(ProductQuery query);

    /** 查询某卖家的全部商品(我的发布，含各状态) */
    List<Product> selectBySeller(Long sellerId);

    /** 更新商品状态(下架/已售) */
    int updateStatus(@Param("productId") Long productId, @Param("status") Integer status);

    /** 浏览量+1 */
    int increaseView(Long productId);
}
