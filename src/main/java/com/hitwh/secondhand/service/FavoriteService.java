package com.hitwh.secondhand.service;

import com.hitwh.secondhand.entity.Favorite;

import java.util.List;

/**
 * 收藏业务接口
 * 负责人：范振扬  日期：6/21
 */
public interface FavoriteService {

    /** 切换收藏：已收藏则取消，未收藏则添加；返回切换后是否为收藏状态 */
    boolean toggle(Long userId, Long productId);

    /** 我的收藏列表 */
    List<Favorite> list(Long userId);

    /** 是否已收藏 */
    boolean isFavorited(Long userId, Long productId);
}
