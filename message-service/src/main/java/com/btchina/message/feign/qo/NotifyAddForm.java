package com.btchina.message.feign.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotifyAddForm {

    @ApiModelProperty(value = "发送者id, 0是系统发送", required = true)
    private Long senderId;

    @ApiModelProperty(value = "接收者id", required = true)
    private Long receiverId;

    @ApiModelProperty(value = "对象id", required = true)
    private Long objectId;

    @ApiModelProperty(value = "操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注", required = true)
    private Integer actionType;

    @ApiModelProperty("消息模板id")
    private Integer templateId;

    @ApiModelProperty(value = "渠道类型 1 站内信 2短信 3邮箱", required = true)
    private Integer channelType;

    @ApiModelProperty(value = "对象类型 1 问题 2 用户", required = true)
    private Integer objectType;

}
