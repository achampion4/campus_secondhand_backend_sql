package com.hitwh.secondhand.enums;

/**
 * 订单状态枚举（6/21 订单模块使用）
 * 负责人：同学D  日期：6/18
 */
public enum OrderStatus {

    UNPAID(0, "待付款"),
    UNSHIPPED(1, "待发货"),
    UNRECEIVED(2, "待收货"),
    COMPLETED(3, "已完成"),
    CANCELLED(4, "已取消");

    private final int code;
    private final String desc;

    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /** 按 code 反查枚举 */
    public static OrderStatus fromCode(int code) {
        for (OrderStatus s : values()) {
            if (s.code == code) {
                return s;
            }
        }
        throw new IllegalArgumentException("非法订单状态: " + code);
    }
}
