package com.hitwh.secondhand.enums;

/**
 * 商品状态枚举（交易模块用于校验"在售/已售"约束）
 * 负责人：同学D  日期：6/18
 */
public enum ProductStatus {

    OFF_SHELF(0, "已下架"),
    ON_SALE(1, "在售"),
    SOLD(2, "已售");

    private final int code;
    private final String desc;

    ProductStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
