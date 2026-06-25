package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.PageResult;
import com.hitwh.secondhand.common.Result;
import com.hitwh.secondhand.dto.ProductForm;
import com.hitwh.secondhand.dto.ProductQuery;
import com.hitwh.secondhand.entity.Product;
import com.hitwh.secondhand.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * 商品接口：发布、编辑、详情、列表、下架
 * 负责人：董炜文  日期：6/19
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /** 发布商品 (需登录) */
    @PostMapping
    public Result<Long> publish(@Valid @RequestBody ProductForm form, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(productService.publish(userId, form));
    }

    /** 编辑商品 (需登录) */
    @PutMapping
    public Result<Void> update(@Valid @RequestBody ProductForm form, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        productService.update(userId, form);
        return Result.success();
    }

    /** 商品列表 (白名单，游客可见) */
    @GetMapping("/list")
    public Result<PageResult<Product>> list(ProductQuery query) {
        return Result.success(productService.page(query));
    }

    /** 我发布的商品 (需登录，含已售/下架) */
    @GetMapping("/mine")
    public Result<java.util.List<Product>> mine(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(productService.mine(userId));
    }

    /** 商品详情 (白名单，游客可见) */
    @GetMapping("/detail/{productId}")
    public Result<Product> detail(@PathVariable Long productId) {
        return Result.success(productService.detail(productId));
    }

    /** 下架商品 (需登录) */
    @PutMapping("/offShelf/{productId}")
    public Result<Void> offShelf(@PathVariable Long productId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        productService.offShelf(userId, productId);
        return Result.success();
    }
}
