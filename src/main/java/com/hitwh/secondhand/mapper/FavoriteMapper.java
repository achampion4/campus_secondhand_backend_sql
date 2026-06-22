package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏数据访问
 * 负责人：范振扬  日期：6/21
 */
@Mapper
public interface FavoriteMapper {

    int insert(Favorite favorite);

    int delete(@Param("userId") Long userId, @Param("productId") Long productId);

    /** 判断是否已收藏 */
    Favorite findByUserAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);

    /** 我的收藏列表（带商品信息） */
    List<Favorite> findByUserId(Long userId);
}
