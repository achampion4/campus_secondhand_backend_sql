package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收货地址数据访问
 * 负责人：董炜文  日期：6/18
 */
@Mapper
public interface AddressMapper {

    /** 查询某用户的全部地址 */
    List<Address> findByUserId(Long userId);

    /** 按ID查询 */
    Address findById(Long addressId);

    /** 新增 */
    int insert(Address address);

    /** 修改 */
    int update(Address address);

    /** 删除 */
    int deleteById(Long addressId);

    /** 取消该用户所有默认地址(设置新默认前调用) */
    int clearDefault(Long userId);
}
