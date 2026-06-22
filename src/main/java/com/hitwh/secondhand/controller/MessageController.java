package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.Result;
import com.hitwh.secondhand.entity.Message;
import com.hitwh.secondhand.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 聊天消息接口（均需登录）
 * 负责人：范振扬  日期：6/21
 */
@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /** 发送消息（body: receiverId, productId, content） */
    @PostMapping
    public Result<Long> send(@RequestBody Message message, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(messageService.send(userId, message));
    }

    /** 与某人针对某商品的会话 */
    @GetMapping("/conversation")
    public Result<List<Message>> conversation(@RequestParam Long other, @RequestParam Long productId,
                                              HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(messageService.conversation(userId, other, productId));
    }

    /** 与我相关的最近消息（前端按对方+商品分组成会话列表） */
    @GetMapping("/recent")
    public Result<List<Message>> recent(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(messageService.recent(userId));
    }
}
