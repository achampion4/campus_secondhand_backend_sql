-- =============================================================
-- 订单相关表建表脚本 (order_tables.sql) —— 配合 6/21 订单模块
-- 负责人：hjh    日期：6/20
-- 前置：已执行 schema.sql(核心表)。本脚本增量添加订单表，满足 3NF。
-- =============================================================

USE campus_secondhand;

-- ---------- 订单表 ----------
CREATE TABLE order_info (
    order_id     BIGINT        NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    buyer_id     BIGINT        NOT NULL COMMENT '买家ID',
    seller_id    BIGINT        NOT NULL COMMENT '卖家ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总额',
    status       TINYINT       NOT NULL DEFAULT 0 COMMENT '状态:0待付款,1待发货,2待收货,3已完成,4已取消',
    address_id   BIGINT        NOT NULL COMMENT '收货地址ID',
    created_at   DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    PRIMARY KEY (order_id),
    KEY idx_order_buyer (buyer_id),
    KEY idx_order_seller (seller_id),
    CONSTRAINT fk_order_buyer   FOREIGN KEY (buyer_id)   REFERENCES `user` (user_id),
    CONSTRAINT fk_order_seller  FOREIGN KEY (seller_id)  REFERENCES `user` (user_id),
    CONSTRAINT fk_order_address FOREIGN KEY (address_id) REFERENCES address (address_id),
    CONSTRAINT chk_order_amount CHECK (total_amount >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ---------- 订单明细表 ----------
CREATE TABLE order_item (
    item_id    BIGINT        NOT NULL AUTO_INCREMENT COMMENT '明细ID',
    order_id   BIGINT        NOT NULL COMMENT '订单ID',
    product_id BIGINT        NOT NULL COMMENT '商品ID',
    price      DECIMAL(10,2) NOT NULL COMMENT '成交价格',
    PRIMARY KEY (item_id),
    KEY idx_item_order (order_id),
    KEY idx_item_product (product_id),
    CONSTRAINT fk_item_order   FOREIGN KEY (order_id)   REFERENCES order_info (order_id),
    CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- 说明：order_info 一对多 order_item，体现"一张订单含多件商品"。
--      6/26 将补充订单相关完整性约束测试(如不能购买自己商品、已售不可重复下单)。
