package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.common.PageResult;
import com.hitwh.secondhand.dto.ProductForm;
import com.hitwh.secondhand.dto.ProductQuery;
import com.hitwh.secondhand.entity.Product;
import com.hitwh.secondhand.entity.ProductImage;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.ProductImageMapper;
import com.hitwh.secondhand.mapper.ProductMapper;
import com.hitwh.secondhand.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品业务实现
 * 负责人：董炜文  日期：6/19
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private final ProductImageMapper productImageMapper;

    public ProductServiceImpl(ProductMapper productMapper, ProductImageMapper productImageMapper) {
        this.productMapper = productMapper;
        this.productImageMapper = productImageMapper;
    }

    @Override
    @Transactional
    public Long publish(Long sellerId, ProductForm form) {
        Product product = new Product();
        product.setSellerId(sellerId);
        product.setCategoryId(form.getCategoryId());
        product.setTitle(form.getTitle());
        product.setDescription(form.getDescription());
        product.setPrice(form.getPrice());
        product.setConditionLevel(form.getConditionLevel());
        productMapper.insert(product);
        // 保存图片
        saveImages(product.getProductId(), form.getImages());
        return product.getProductId();
    }

    @Override
    @Transactional
    public void update(Long sellerId, ProductForm form) {
        Product db = productMapper.findById(form.getProductId());
        if (db == null) {
            throw new BusinessException("商品不存在");
        }
        if (!db.getSellerId().equals(sellerId)) {
            throw new BusinessException("无权编辑他人商品");
        }
        Product product = new Product();
        product.setProductId(form.getProductId());
        product.setCategoryId(form.getCategoryId());
        product.setTitle(form.getTitle());
        product.setDescription(form.getDescription());
        product.setPrice(form.getPrice());
        product.setConditionLevel(form.getConditionLevel());
        productMapper.update(product);
        // 图片先清后插
        productImageMapper.deleteByProductId(form.getProductId());
        saveImages(form.getProductId(), form.getImages());
    }

    @Override
    public Product detail(Long productId) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        productMapper.increaseView(productId);
        product.setImages(productImageMapper.findUrlsByProductId(productId));
        return product;
    }

    @Override
    public PageResult<Product> page(ProductQuery query) {
        long total = productMapper.countByQuery(query);
        List<Product> records = productMapper.selectByQuery(query);
        return new PageResult<>(total, query.getPage(), query.getSize(), records);
    }

    @Override
    public void offShelf(Long sellerId, Long productId) {
        Product db = productMapper.findById(productId);
        if (db == null) {
            throw new BusinessException("商品不存在");
        }
        if (!db.getSellerId().equals(sellerId)) {
            throw new BusinessException("无权下架他人商品");
        }
        productMapper.updateStatus(productId, 0); // 0=下架
    }

    @Override
    public List<Product> mine(Long sellerId) {
        return productMapper.selectBySeller(sellerId);
    }

    /** 保存商品图片列表 */
    private void saveImages(Long productId, List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            return;
        }
        List<ProductImage> images = new ArrayList<>();
        for (int i = 0; i < urls.size(); i++) {
            ProductImage img = new ProductImage();
            img.setProductId(productId);
            img.setUrl(urls.get(i));
            img.setSortOrder(i);
            images.add(img);
        }
        productImageMapper.batchInsert(images);
    }
}
