package com.btchina.feign.model.message.qo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotifyAddForm {

    @Schema(description = "发送者id, 0是系统发送", required = true)
    private Long senderId;

    @Schema(description = "接收者id", required = true)
    private Long receiverId;

    @Schema(description = "对象id", required = true)
    private Long objectId;

    @Schema(description = "操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注", required = true)
    private Integer actionType;

    @Schema(description = "消息模板id")
    private Integer templateId;

    @Schema(description = "渠道类型 1 站内信 2短信 3邮箱", required = true)
    private Integer channelType;

    @Schema(description = "对象类型 1 问题 2 用户", required = true)
    private Integer objectType;

}
