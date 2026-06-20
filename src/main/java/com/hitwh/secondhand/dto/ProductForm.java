package com.hitwh.secondhand.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品发布/编辑表单
 * 负责人：董炜文  日期：6/19
 */
@Data
public class ProductForm {

    private Long productId; // 编辑时传，发布时为空

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotNull(message = "请选择分类")
    private Long categoryId;

    @NotNull(message = "请输入价格")
    private BigDecimal price;

    @NotNull(message = "请选择成色")
    private Integer conditionLevel;

    private String description;

    private List<String> images; // 图片URL列表
}
