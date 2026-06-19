package com.hitwh.secondhand.service;

import com.hitwh.secondhand.entity.Address;

import java.util.List;

/**
 * 收货地址业务接口
 * 负责人：董炜文  日期：6/18
 */
public interface AddressService {

    /** 查询当前用户全部地址 */
    List<Address> list(Long userId);

    /** 新增地址 */
    Long add(Long userId, Address address);

    /** 修改地址 */
    void update(Long userId, Address address);

    /** 删除地址 */
    void delete(Long userId, Long addressId);
}
