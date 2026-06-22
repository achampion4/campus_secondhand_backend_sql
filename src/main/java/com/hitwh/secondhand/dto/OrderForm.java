package com.hitwh.secondhand.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 下单表单（二手商品一口价、单件购买）
 * 负责人：范振扬  日期：6/21
 */
@Data
public class OrderForm {

    @NotNull(message = "请选择商品")
    private Long productId;

    @NotNull(message = "请选择收货地址")
    private Long addressId;
}
