-- =============================================================
-- 校园二手交易平台 —— 初始化数据 (init_data.sql)
-- 负责人：hjh    日期：6/17
-- 执行前请先执行 schema.sql
-- 说明：示例密码均为 123456 (BCrypt 加密后存储)
-- =============================================================

USE campus_secondhand;

-- ---------- 用户 ----------
-- 密码明文均为 123456
INSERT INTO `user` (username, password, nickname, role, status) VALUES
('admin', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '管理员', 1, 1),
('zhangsan', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '张三', 0, 1),
('lisi', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '李四', 0, 1),
('wangwu', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '王五', 0, 1);

-- ---------- 商品分类 ----------
INSERT INTO category (category_id, name, parent_id) VALUES
(1, '教材书籍', 0),
(2, '电子数码', 0),
(3, '生活用品', 0),
(4, '运动户外', 0),
(5, '代步工具', 0),
(11, '考研资料', 1),
(12, '专业教材', 1),
(21, '手机平板', 2),
(22, '电脑配件', 2),
(23, '耳机音箱', 2);

-- ---------- 收货地址 ----------
INSERT INTO address (user_id, receiver, phone, region, detail, is_default) VALUES
(2, '张三', '13800000001', '威海校区', '北苑5号楼301', 1),
(3, '李四', '13800000002', '威海校区', '南苑2号楼108', 1);

-- ---------- 商品 ----------
INSERT INTO product (seller_id, category_id, title, description, price, condition_level, status) VALUES
(2, 11, '考研数学全套资料', '李永乐复习全书+660题，几乎全新', 80.00, 2, 1),
(2, 21, '二手iPad 2021', '64G WiFi版，无磕碰，含保护壳', 1500.00, 3, 1),
(3, 23, '索尼蓝牙耳机', 'WF-1000XM4，使用一年，功能正常', 600.00, 3, 1),
(3, 5,  '通勤自行车', '骑行半年，9成新，适合校内代步', 200.00, 3, 1);

-- ---------- 商品图片 ----------
INSERT INTO product_image (product_id, url, sort_order) VALUES
(1, '/images/sample1.jpg', 0),
(2, '/images/sample2.jpg', 0),
(3, '/images/sample3.jpg', 0),
(4, '/images/sample4.jpg', 0);
