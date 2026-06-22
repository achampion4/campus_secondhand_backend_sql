-- =============================================================
-- 社交与举报相关表 (social_tables.sql)
-- 负责人：hjh    覆盖：收藏、聊天消息、评价、举报
-- 前置：已执行 schema.sql、order_tables.sql。满足 3NF。
-- =============================================================

USE campus_secondhand;

-- ---------- 收藏表 ----------
CREATE TABLE favorite (
    favorite_id BIGINT   NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    user_id     BIGINT   NOT NULL COMMENT '用户ID',
    product_id  BIGINT   NOT NULL COMMENT '商品ID',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (favorite_id),
    UNIQUE KEY uk_fav_user_product (user_id, product_id),
    KEY idx_fav_product (product_id),
    CONSTRAINT fk_fav_user    FOREIGN KEY (user_id)    REFERENCES `user` (user_id),
    CONSTRAINT fk_fav_product FOREIGN KEY (product_id) REFERENCES product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏表';

-- ---------- 聊天消息表 ----------
CREATE TABLE message (
    message_id  BIGINT   NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    sender_id   BIGINT   NOT NULL COMMENT '发送者',
    receiver_id BIGINT   NOT NULL COMMENT '接收者',
    product_id  BIGINT   NOT NULL COMMENT '关联商品',
    content     VARCHAR(500) NOT NULL COMMENT '消息内容',
    is_read     TINYINT  NOT NULL DEFAULT 0 COMMENT '是否已读:0否,1是',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
    PRIMARY KEY (message_id),
    KEY idx_msg_receiver (receiver_id),
    KEY idx_msg_product (product_id),
    CONSTRAINT fk_msg_sender   FOREIGN KEY (sender_id)   REFERENCES `user` (user_id),
    CONSTRAINT fk_msg_receiver FOREIGN KEY (receiver_id) REFERENCES `user` (user_id),
    CONSTRAINT fk_msg_product  FOREIGN KEY (product_id)  REFERENCES product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='聊天消息表';

-- ---------- 评价表 ----------
CREATE TABLE review (
    review_id   BIGINT   NOT NULL AUTO_INCREMENT COMMENT '评价ID',
    order_id    BIGINT   NOT NULL COMMENT '关联订单',
    reviewer_id BIGINT   NOT NULL COMMENT '评价人',
    target_id   BIGINT   NOT NULL COMMENT '被评价人',
    rating      TINYINT  NOT NULL COMMENT '评分1-5',
    content     VARCHAR(500) DEFAULT NULL COMMENT '评价内容',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
    PRIMARY KEY (review_id),
    KEY idx_review_target (target_id),
    CONSTRAINT fk_review_order    FOREIGN KEY (order_id)    REFERENCES order_info (order_id),
    CONSTRAINT fk_review_reviewer FOREIGN KEY (reviewer_id) REFERENCES `user` (user_id),
    CONSTRAINT fk_review_target   FOREIGN KEY (target_id)   REFERENCES `user` (user_id),
    CONSTRAINT chk_review_rating CHECK (rating BETWEEN 1 AND 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- ---------- 举报表 ----------
CREATE TABLE report (
    report_id   BIGINT   NOT NULL AUTO_INCREMENT COMMENT '举报ID',
    reporter_id BIGINT   NOT NULL COMMENT '举报人',
    product_id  BIGINT   NOT NULL COMMENT '被举报商品',
    reason      VARCHAR(255) NOT NULL COMMENT '举报原因',
    status      TINYINT  NOT NULL DEFAULT 0 COMMENT '状态:0待处理,1已处理,2已驳回',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '举报时间',
    PRIMARY KEY (report_id),
    KEY idx_report_product (product_id),
    CONSTRAINT fk_report_reporter FOREIGN KEY (reporter_id) REFERENCES `user` (user_id),
    CONSTRAINT fk_report_product  FOREIGN KEY (product_id)  REFERENCES product (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='举报表';
