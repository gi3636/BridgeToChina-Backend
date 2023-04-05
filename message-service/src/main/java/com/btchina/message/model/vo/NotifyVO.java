package com.btchina.message.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class NotifyVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("发送者id, 0是系统发送")
    private Long senderId;

    @ApiModelProperty("发送者名称")
    private String senderName;

    @ApiModelProperty("发送者头像")
    private String senderAvatar;

    @ApiModelProperty("接收者id")
    private Long receiverId;

    @ApiModelProperty("对象id")
    private Long objectId;

    @ApiModelProperty("操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注")
    private Integer actionType;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("对象类型 1 问题 2 用户")
    private Integer objectType;

    @ApiModelProperty("是否已读  1已读 0未读 ")
    private Boolean isRead;

    @ApiModelProperty("阅读时间")
    private Date readTime;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    private Date updatedTime;
}
