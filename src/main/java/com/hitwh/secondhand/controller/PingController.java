package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 健康检查接口：用于验证后端骨架是否正常运行
 * 负责人：同学C  日期：6/17
 */
@RestController
@RequestMapping("/api/ping")
public class PingController {

    @GetMapping
    public Result<Map<String, Object>> ping() {
        Map<String, Object> data = new HashMap<>();
        data.put("service", "campus-secondhand");
        data.put("status", "running");
        data.put("time", LocalDateTime.now().toString());
        return Result.success(data);
    }
}
