package com.hitwh.secondhand.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 注册请求参数
 * 负责人：董炜文  日期：6/18
 */
@Data
public class RegisterDTO {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度为3-20位")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度为6-20位")
    private String password;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    // 建议1：实名认证（上传不校验，由同校双方自辨真伪）
    @NotBlank(message = "请填写真实姓名")
    private String realName;

    @NotBlank(message = "请填写学号")
    private String studentId;

    private String studentCardImg; // 学生证照片URL
}
