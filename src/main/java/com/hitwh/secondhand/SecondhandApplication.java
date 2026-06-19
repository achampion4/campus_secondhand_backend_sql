package com.hitwh.secondhand;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 校园二手交易平台 后端启动类
 * 负责人：同学C  日期：6/17
 */
@SpringBootApplication
@MapperScan("com.hitwh.secondhand.mapper")
public class SecondhandApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondhandApplication.class, args);
        System.out.println("==== 校园二手交易平台后端启动成功 ====");
    }
}
