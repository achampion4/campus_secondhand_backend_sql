package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.Result;
import com.hitwh.secondhand.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传接口：图片上传 (需登录)
 * 负责人：董炜文  日期：6/19
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    // 上传根目录，可在 application.yml 配置 file.upload-dir 覆盖
    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    /**
     * 上传图片，返回可访问的 URL 路径(如 /uploads/xxx.jpg)
     */
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("上传文件为空");
        }
        String original = file.getOriginalFilename();
        String suffix = (original != null && original.contains("."))
                ? original.substring(original.lastIndexOf(".")) : ".jpg";
        String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(dir.getAbsolutePath(), fileName));
        } catch (IOException e) {
            throw new BusinessException("文件保存失败：" + e.getMessage());
        }
        // 返回访问路径，前端拼接后端地址即可显示
        return Result.success("/uploads/" + fileName);
    }
}
