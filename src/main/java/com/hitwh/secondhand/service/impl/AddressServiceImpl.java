package com.hitwh.secondhand.service.impl;

import com.hitwh.secondhand.entity.Address;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.AddressMapper;
import com.hitwh.secondhand.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址业务实现
 * 负责人：董炜文  日期：6/18
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public List<Address> list(Long userId) {
        return addressMapper.findByUserId(userId);
    }

    @Override
    public Long add(Long userId, Address address) {
        address.setUserId(userId);
        // 若设为默认，先清掉其它默认
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            addressMapper.clearDefault(userId);
        }
        addressMapper.insert(address);
        return address.getAddressId();
    }

    @Override
    public void update(Long userId, Address address) {
        checkOwner(userId, address.getAddressId());
        if (address.getIsDefault() != null && address.getIsDefault() == 1) {
            addressMapper.clearDefault(userId);
        }
        addressMapper.update(address);
    }

    @Override
    public void delete(Long userId, Long addressId) {
        checkOwner(userId, addressId);
        addressMapper.deleteById(addressId);
    }

    /** 校验该地址是否属于当前用户，防止越权操作 */
    private void checkOwner(Long userId, Long addressId) {
        Address db = addressMapper.findById(addressId);
        if (db == null) {
            throw new BusinessException("地址不存在");
        }
        if (!db.getUserId().equals(userId)) {
            throw new BusinessException("无权操作该地址");
        }
    }
}
