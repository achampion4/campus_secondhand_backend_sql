package com.hitwh.secondhand.mapper;

import com.hitwh.secondhand.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 聊天消息数据访问
 * 负责人：范振扬  日期：6/21
 */
@Mapper
public interface MessageMapper {

    int insert(Message message);

    /** 两人针对某商品的会话记录 */
    List<Message> conversation(@Param("me") Long me, @Param("other") Long other, @Param("productId") Long productId);

    /** 与我相关的最近消息（用于会话列表，前端按对方+商品分组） */
    List<Message> recentInvolving(Long userId);

    /** 把对方发给我的消息标记为已读 */
    int markRead(@Param("me") Long me, @Param("other") Long other, @Param("productId") Long productId);
}
