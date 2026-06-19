package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.Result;
import com.hitwh.secondhand.entity.Address;
import com.hitwh.secondhand.service.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收货地址接口：增删改查 (均需登录)
 * 负责人：董炜文  日期：6/18
 */
@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /** 当前用户的地址列表 */
    @GetMapping("/list")
    public Result<List<Address>> list(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(addressService.list(userId));
    }

    /** 新增地址 */
    @PostMapping
    public Result<Long> add(@RequestBody Address address, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(addressService.add(userId, address));
    }

    /** 修改地址 */
    @PutMapping
    public Result<Void> update(@RequestBody Address address, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        addressService.update(userId, address);
        return Result.success();
    }

    /** 删除地址 */
    @DeleteMapping("/{addressId}")
    public Result<Void> delete(@PathVariable Long addressId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        addressService.delete(userId, addressId);
        return Result.success();
    }
}
