package com.hitwh.secondhand.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitwh.secondhand.dto.AiRecommendGroup;
import com.hitwh.secondhand.dto.ProductQuery;
import com.hitwh.secondhand.entity.Product;
import com.hitwh.secondhand.exception.BusinessException;
import com.hitwh.secondhand.mapper.ProductMapper;
import com.hitwh.secondhand.service.AiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * AI 智能导购实现：把平台所有在售商品发给 DeepSeek，由大模型直接挑选最相关的商品并给出推荐理由
 * （演示项目数据量小，整表发送，规避关键词匹配不到的问题）
 * 负责人：hjh  日期：6/22
 */
@Service
public class AiServiceImpl implements AiService {

    private static final Logger log = LoggerFactory.getLogger(AiServiceImpl.class);

    @Value("${deepseek.api-key}")
    private String apiKey;
    @Value("${deepseek.url}")
    private String apiUrl;
    @Value("${deepseek.model}")
    private String model;

    private final ProductMapper productMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    public AiServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public List<AiRecommendGroup> recommend(String need) {
        if (need == null || need.isBlank()) {
            throw new BusinessException("请描述你的需求");
        }
        log.info("===== [AI导购] 收到需求: {} =====", need);

        // 1. 取出所有在售商品（演示数据量小，最多取200条全部发给 AI）
        ProductQuery query = new ProductQuery();
        query.setPage(1);
        query.setSize(200);
        List<Product> all = productMapper.selectByQuery(query);
        log.info("[AI导购] 当前在售商品数量: {}", all.size());
        if (all.isEmpty()) {
            log.info("[AI导购] 无在售商品，直接返回空");
            return new ArrayList<>();
        }

        // 2. 组装精简商品清单（只发 id/标题/分类/价格，减小体积）
        List<Map<String, Object>> brief = all.stream().map(p -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", p.getProductId());
            m.put("title", p.getTitle());
            m.put("category", p.getCategoryName());
            m.put("price", p.getPrice());
            return m;
        }).collect(Collectors.toList());

        // 3. 调用 DeepSeek 让其挑选
        JsonNode recommendations = callDeepSeek(need, brief);

        // 4. 把 AI 选中的 id 映射回完整商品（保持 AI 给出的顺序）
        Map<Long, Product> idMap = all.stream()
                .collect(Collectors.toMap(Product::getProductId, Function.identity(), (a, b) -> a));
        List<AiRecommendGroup> result = new ArrayList<>();
        if (recommendations != null && recommendations.isArray()) {
            for (JsonNode r : recommendations) {
                Long pid = r.path("productId").asLong();
                String reason = r.path("reason").asText("");
                Product p = idMap.get(pid);
                if (p != null) {
                    result.add(new AiRecommendGroup(p, reason));
                }
            }
        }
        log.info("[AI导购] 最终推荐商品数: {}", result.size());
        return result;
    }

    /** 调用 DeepSeek，返回 recommendations 数组节点 */
    private JsonNode callDeepSeek(String need, List<Map<String, Object>> brief) {
        try {
            String productListJson = objectMapper.writeValueAsString(brief);
            String systemPrompt = "你是校园二手交易平台的导购助手。以下是平台所有在售商品（JSON数组，含id/title/category/price）："
                    + productListJson
                    + "。请根据用户需求，从中挑选最相关的商品（最多8件，按相关度排序）。"
                    + "只返回JSON，格式为 {\"recommendations\":[{\"productId\":商品id, \"reason\":\"20字内推荐理由\"}]}，不要输出任何多余文字。";

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

            log.info("[AI导购] 发送给 DeepSeek，商品清单条数: {}", brief.size());
            long start = System.currentTimeMillis();
            ResponseEntity<String> resp = restTemplate.postForEntity(apiUrl,
                    new HttpEntity<>(body, headers), String.class);
            log.info("[AI导购] DeepSeek 响应耗时: {} ms", System.currentTimeMillis() - start);

            JsonNode root = objectMapper.readTree(resp.getBody());
            String content = root.path("choices").get(0).path("message").path("content").asText();
            log.info("[AI导购] DeepSeek 返回内容: {}", content);

            return objectMapper.readTree(content).path("recommendations");
        } catch (Exception e) {
            log.error("[AI导购] 调用 DeepSeek 失败", e);
            throw new BusinessException("AI 服务暂时不可用，请稍后再试");
        }
    }
}
