-- =============================================================
-- 完整性约束测试脚本 (integrity_test.sql) —— 针对 6/17 核心表
-- 负责人：霍俊豪    日期：6/18
-- 用法：执行 schema.sql + init_data.sql 后运行本脚本，
--       每个用例的预期是"报错/被拒绝"，以此验证约束生效。
-- =============================================================

USE campus_secondhand;

-- 用例1：用户名唯一约束 —— 插入重复 username，预期报错(Duplicate entry)
-- INSERT INTO `user` (username, password, nickname) VALUES ('zhangsan', 'x', '重复张三');

-- 用例2：商品价格 CHECK 约束 —— 价格为负，预期报错(chk_prod_price)
-- INSERT INTO product (seller_id, category_id, title, price) VALUES (2, 11, '测试负价', -1.00);

-- 用例3：外键约束（卖家不存在）—— seller_id=9999 不存在，预期报错(fk_prod_seller)
-- INSERT INTO product (seller_id, category_id, title, price) VALUES (9999, 11, '幽灵卖家', 10.00);

-- 用例4：外键约束（分类不存在）—— category_id=8888 不存在，预期报错(fk_prod_category)
-- INSERT INTO product (seller_id, category_id, title, price) VALUES (2, 8888, '幽灵分类', 10.00);

-- 用例5：地址外键（用户不存在）—— 预期报错(fk_addr_user)
-- INSERT INTO address (user_id, receiver, phone, region, detail)
--   VALUES (7777, '某人', '13800000000', '校区', '楼栋');

-- 用例6：非空约束 —— 标题为 NULL，预期报错(title cannot be null)
-- INSERT INTO product (seller_id, category_id, title, price) VALUES (2, 11, NULL, 10.00);

-- 说明：上述语句默认注释，验收/测试时逐条取消注释执行，
--      记录"是否如预期报错"以证明完整性约束有效（用于课程报告测试章节）。
-- 后续 6/26 将补充订单/社交相关表的约束测试用例。
