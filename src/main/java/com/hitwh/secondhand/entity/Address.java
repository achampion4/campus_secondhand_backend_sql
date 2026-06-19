package com.hitwh.secondhand.entity;

import lombok.Data;

/**
 * 收货地址实体，对应表 address
 * 负责人：董炜文  日期：6/18
 */
@Data
public class Address {
    private Long addressId;   // 地址ID
    private Long userId;      // 所属用户
    private String receiver;  // 收货人
    private String phone;     // 联系电话
    private String region;    // 地区(校区/楼栋)
    private String detail;    // 详细地址
    private Integer isDefault; // 0否,1是
}
