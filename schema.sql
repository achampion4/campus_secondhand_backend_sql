-- =============================================================
-- 校园二手交易平台 —— 数据库建表脚本 (schema.sql)
-- 负责人：同学E    日期：6/17（核心基础表）
-- 数据库：MySQL 8.0+   字符集：utf8mb4
-- 设计满足 3NF，含主键、外键、唯一约束、检查约束与索引
--
-- 【分批建表说明】本日仅建立其他模块立即依赖的 5 张核心表，
-- 其余表随对应功能模块开发逐步补充：
--   6/21 订单模块：order_info、order_item
--   6/23 社交模块：favorite、message、review
--   6/25 后台模块：report
-- =============================================================

DROP DATABASE IF EXISTS campus_secondhand;
CREATE DATABASE campus_secondhand DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE campus_secondhand;

-- ---------- 1. 用户表 ----------
CREATE TABLE `user` (
    user_id       BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username      VARCHAR(50)  NOT NULL COMMENT '登录账号',
    password      VARCHAR(100) NOT NULL COMMENT '密码(BCrypt加密)',
    nickname      VARCHAR(50)  NOT NULL COMMENT '昵称',
    avatar        VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    credit_score  INT          NOT NULL DEFAULT 100 COMMENT '信用分',
    role          TINYINT      NOT NULL DEFAULT 0 COMMENT '角色:0普通用户,1管理员',
    status        TINYINT      NOT NULL DEFAULT 1 COMMENT '状态:0封禁,1正常',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    PRIMARY KEY (user_id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ---------- 2. 收货地址表 ----------
CREATE TABLE address (
    address_id  BIGINT       NOT NULL AUTO_INCREMENT COMMENT '地址ID',
    user_id     BIGINT       NOT NULL COMMENT '所属用户',
    receiver    VARCHAR(50)  NOT NULL COMMENT '收货人',
    phone       VARCHAR(20)  NOT NULL COMMENT '联系电话',
    region      VARCHAR(100) NOT NULL COMMENT '地区(校区/楼栋)',
    detail      VARCHAR(255) NOT NULL COMMENT '详细地址',
    is_default  TINYINT      NOT NULL DEFAULT 0 COMMENT '是否默认:0否,1是',
    PRIMARY KEY (address_id),
    KEY idx_addr_user (user_id),
    CONSTRAINT fk_addr_user FOREIGN KEY (user_id) REFERENCES `user` (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货地址表';

-- ---------- 3. 商品分类表 ----------
CREATE TABLE category (
    category_id BIGINT      NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    name        VARCHAR(50) NOT NULL COMMENT '分类名称',
    parent_id   BIGINT      NOT NULL DEFAULT 0 COMMENT '父分类ID,0为顶级',
    PRIMARY KEY (category_id),
    KEY idx_cat_parent (parent_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- ---------- 4. 商品表 ----------
CREATE TABLE product (
    product_id      BIGINT        NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    seller_id       BIGINT        NOT NULL COMMENT '卖家ID',
    category_id     BIGINT        NOT NULL COMMENT '分类ID',
    title           VARCHAR(100)  NOT NULL COMMENT '标题',
    description     TEXT          COMMENT '描述',
    price           DECIMAL(10,2) NOT NULL COMMENT '价格',
    condition_level TINYINT       NOT NULL DEFAULT 1 COMMENT '成色:1全新,2几乎全新,3轻微使用,4明显使用',
    status          TINYINT       NOT NULL DEFAULT 1 COMMENT '状态:0下架,1在售,2已售',
    view_count      INT           NOT NULL DEFAULT 0 COMMENT '浏览量',
    created_at      DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    PRIMARY KEY (product_id),
    KEY idx_prod_seller (seller_id),
    KEY idx_prod_category (category_id),
    KEY idx_prod_title (title),
    CONSTRAINT fk_prod_seller   FOREIGN KEY (seller_id)   REFERENCES `user` (user_id),
    CONSTRAINT fk_prod_category FOREIGN KEY (category_id) REFERENCES category (category_id),
    CONSTRAINT chk_prod_price CHECK (price >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- ---------- 5. 商品图片表 ----------
CREATE TABLE product_image (
    image_id   BIGINT       NOT NULL AUTO_INCREMENT COMMENT '图片ID',
    product_id BIGINT       NOT NULL COMMENT '所属商品',
    url        VARCHAR(255) NOT NULL COMMENT '图片URL',
    sort_order INT          NOT NULL DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (image_id),
    KEY idx_img_product (product_id),
    CONSTRAINT fk_img_product FOREIGN KEY (product_id) REFERENCES product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品图片表';

-- 其余表（订单/社交/举报）随对应模块开发在后续日期补充，见文件头说明。
