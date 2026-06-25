-- =============================================================
-- 增量改库脚本 alter_v2.sql
-- 负责人：霍俊豪    日期：6/23
-- 在已有 campus_secondhand 库上执行；新部署可把这些改动并入 schema.sql
-- =============================================================

USE campus_secondhand;

-- 用户表增加实名认证三列
ALTER TABLE `user`
    ADD COLUMN real_name        VARCHAR(50)  NULL COMMENT '真实姓名' AFTER nickname,
    ADD COLUMN student_id       VARCHAR(30)  NULL COMMENT '学号(可含字母)' AFTER real_name,
    ADD COLUMN student_card_img VARCHAR(255) NULL COMMENT '学生证照片URL' AFTER student_id;

-- 取消交易流程后，订单退化为“成交记录”，收货地址不再必填
ALTER TABLE order_info MODIFY COLUMN address_id BIGINT NULL COMMENT '收货地址ID(纯交流模式下可空)';

-- 黑名单表
CREATE TABLE blacklist (
    id          BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id     BIGINT   NOT NULL COMMENT '拉黑者',
    blocked_id  BIGINT   NOT NULL COMMENT '被拉黑者',
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '拉黑时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_blocked (user_id, blocked_id),
    KEY idx_bl_blocked (blocked_id),
    CONSTRAINT fk_bl_user    FOREIGN KEY (user_id)    REFERENCES `user` (user_id),
    CONSTRAINT fk_bl_blocked FOREIGN KEY (blocked_id) REFERENCES `user` (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='黑名单表';

-- 说明：实名信息上传不做真伪校验(由同校双方自辨)；信用沿用“卖家评价平均星级”。
