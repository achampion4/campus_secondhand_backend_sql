-- =============================================================
-- 商品模块测试脚本 (product_test.sql)
-- 负责人：hjh    日期：6/19
-- 配合 more_products.sql 使用：先灌测试数据，再执行本脚本验证
--   一、功能查询测试（验证搜索/筛选/分页 SQL 是否符合预期）
--   二、完整性约束测试（验证商品相关约束生效）
-- =============================================================

USE campus_secondhand;

-- ========== 一、功能查询测试（执行后人工核对结果是否符合预期）==========

-- F-01 在售商品总数（应为 init_data 4 条 + more_products 15 条 = 19 条）
SELECT COUNT(*) AS on_sale_total FROM product WHERE status = 1;

-- F-02 关键词搜索"教材"（标题模糊匹配，应返回含"教材"的商品）
SELECT product_id, title, price FROM product
WHERE status = 1 AND title LIKE CONCAT('%', '教材', '%')
ORDER BY created_at DESC;

-- F-03 按分类筛选（category_id = 22 电脑配件，应只返回该分类在售商品）
SELECT product_id, title, category_id FROM product
WHERE status = 1 AND category_id = 22;

-- F-04 分页第1页（每页12条），核对返回不超过12条且按时间倒序
SELECT product_id, title, created_at FROM product
WHERE status = 1
ORDER BY created_at DESC
LIMIT 0, 12;

-- F-05 分页第2页（偏移12），核对返回剩余记录
SELECT product_id, title FROM product
WHERE status = 1
ORDER BY created_at DESC
LIMIT 12, 12;

-- F-06 商品详情联表（带分类名、卖家昵称），以 product_id=1 为例
SELECT p.product_id, p.title, c.name AS category_name, u.nickname AS seller_nickname
FROM product p
LEFT JOIN category c ON p.category_id = c.category_id
LEFT JOIN `user` u ON p.seller_id = u.user_id
WHERE p.product_id = 1;

-- ========== 二、完整性约束测试（预期报错，逐条取消注释验证）==========

-- C-01 价格为负，预期触发 chk_prod_price 报错
-- INSERT INTO product (seller_id, category_id, title, price) VALUES (2, 11, '负价测试', -5.00);

-- C-02 分类不存在，预期触发 fk_prod_category 报错
-- INSERT INTO product (seller_id, category_id, title, price) VALUES (2, 9999, '幽灵分类', 10.00);

-- C-03 卖家不存在，预期触发 fk_prod_seller 报错
-- INSERT INTO product (seller_id, category_id, title, price) VALUES (8888, 11, '幽灵卖家', 10.00);

-- C-04 商品图片外键，product_id 不存在，预期触发 fk_img_product 报错
-- INSERT INTO product_image (product_id, url, sort_order) VALUES (99999, '/x.jpg', 0);

-- 说明：功能测试结果与约束测试现象记录到课程报告"测试结果与分析"章节。
