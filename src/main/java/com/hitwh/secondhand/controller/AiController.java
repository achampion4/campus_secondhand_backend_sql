package com.hitwh.secondhand.controller;

import com.hitwh.secondhand.common.Result;
import com.hitwh.secondhand.dto.AiRecommendGroup;
import com.hitwh.secondhand.service.AiService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * AI 智能导购接口
 * 负责人：hjh  日期：6/22
 */
@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    /** 根据用户需求推荐商品（body: {"need":"我搬家了，需要照明的东西"}） */
    @PostMapping("/recommend")
    public Result<List<AiRecommendGroup>> recommend(@RequestBody Map<String, String> body) {
        return Result.success(aiService.recommend(body.get("need")));
    }
}
