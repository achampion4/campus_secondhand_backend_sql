package com.hitwh.secondhand.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitwh.secondhand.dto.AiRecommendGroup;
import com.hitwh.secondhand.dto.ProductQuery;
import com.hitwh.secondhand.entity.Category;
import com.hitwh.secondhand.entity.Product;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.ProductMapper;
import com.hitwh.secondhand.service.AiService;
import com.hitwh.secondhand.service.CategoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AI 智能导购实现：调用 DeepSeek 把自然语言需求转为商品关键词，再检索数据库推荐商品
 * 负责人：hjh  日期：6/22
 */
@Service
public class AiServiceImpl implements AiService {

    @Value("${deepseek.api-key}")
    private String apiKey;
    @Value("${deepseek.url}")
    private String apiUrl;
    @Value("${deepseek.model}")
    private String model;

    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public AiServiceImpl(CategoryService categoryService, ProductMapper productMapper) {
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    @Override
    public List<AiRecommendGroup> recommend(String need) {
        if (need == null || need.isBlank()) {
            throw new BusinessException("请描述你的需求");
        }
        // 1. 调用大模型拿到关键词
        List<String> keywords = extractKeywords(need);
        // 2. 逐个关键词检索在售商品，命中才保留
        List<AiRecommendGroup> result = new ArrayList<>();
        for (String kw : keywords) {
            ProductQuery query = new ProductQuery();
            query.setKeyword(kw);
            query.setPage(1);
            query.setSize(3);
            List<Product> products = productMapper.selectByQuery(query);
            if (!products.isEmpty()) {
                result.add(new AiRecommendGroup(kw, products));
            }
        }
        return result;
    }

    /** 调用 DeepSeek，返回提取出的关键词列表 */
    private List<String> extractKeywords(String need) {
        // 动态读取平台真实分类，拼进提示词，避免推荐库里没有的类目
        String catNames = categoryService.listAll().stream()
                .map(Category::getName).collect(Collectors.joining("、"));
        String systemPrompt = "你是校园二手交易平台的导购助手。平台现有商品分类：" + catNames + "。"
                + "请根据用户的需求，推荐3-5个最相关的商品关键词（尽量贴近上述分类或常见二手物品名）。"
                + "只返回JSON，格式为 {\"keywords\":[\"关键词1\",\"关键词2\"]}，不要输出任何多余文字。";

        // 组装请求体（OpenAI 兼容格式，启用 JSON 输出模式保证可解析）
        Map<String, Object> body = new HashMap<>();
        body.put("model", model);
        body.put("temperature", 0.3);
        body.put("response_format", Collections.singletonMap("type", "json_object"));
        body.put("messages", Arrays.asList(
                Map.of("role", "system", "content", systemPrompt),
                Map.of("role", "user", "content", need)
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        try {
            ResponseEntity<String> resp = restTemplate.postForEntity(apiUrl,
                    new HttpEntity<>(body, headers), String.class);
            // 取出 choices[0].message.content（是一段 JSON 字符串）
            JsonNode root = objectMapper.readTree(resp.getBody());
            String content = root.path("choices").get(0).path("message").path("content").asText();
            JsonNode keywordsNode = objectMapper.readTree(content).path("keywords");
            List<String> keywords = new ArrayList<>();
            keywordsNode.forEach(n -> {
                String k = n.asText().trim();
                if (!k.isEmpty()) keywords.add(k);
            });
            return keywords;
        } catch (Exception e) {
            throw new BusinessException("AI 服务暂时不可用，请稍后再试");
        }
    }
}
