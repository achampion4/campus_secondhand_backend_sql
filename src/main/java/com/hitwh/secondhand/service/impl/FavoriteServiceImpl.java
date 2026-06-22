package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.entity.Favorite;
import com.hitwh.secondhand.mapper.FavoriteMapper;
import com.hitwh.secondhand.service.FavoriteService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏业务实现
 * 负责人：范振扬  日期：6/21
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;

    public FavoriteServiceImpl(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    @Override
    public boolean toggle(Long userId, Long productId) {
        Favorite exist = favoriteMapper.findByUserAndProduct(userId, productId);
        if (exist != null) {
            favoriteMapper.delete(userId, productId);
            return false; // 已取消收藏
        }
        Favorite f = new Favorite();
        f.setUserId(userId);
        f.setProductId(productId);
        favoriteMapper.insert(f);
        return true; // 已收藏
    }

    @Override
    public List<Favorite> list(Long userId) {
        return favoriteMapper.findByUserId(userId);
    }

    @Override
    public boolean isFavorited(Long userId, Long productId) {
        return favoriteMapper.findByUserAndProduct(userId, productId) != null;
    }
}
