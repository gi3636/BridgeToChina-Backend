package com.btchina.notification.model.vo;

import com.btchina.feign.model.question.vo.QuestionVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class NotifyVO {

    @Schema(description ="id")
    private Long id;

    @Schema(description ="发送者id, 0是系统发送")
    private Long senderId;

    @Schema(description ="发送者名称")
    private String senderName;

    @Schema(description ="发送者头像")
    private String senderAvatar;

    @Schema(description ="接收者id")
    private Long receiverId;

    @Schema(description ="对象id")
    private Long objectId;

    @Schema(description ="操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注")
    private Integer actionType;

    @Schema(description ="操作名称")
    private String actionName;

    @Schema(description ="消息内容")
    private String content;

    @Schema(description ="对象类型 1 问题 2 用户")
    private Integer objectType;

    @Schema(description ="是否已读  1已读 0未读 ")
    private Boolean isRead;

    @Schema(description ="问题信息")
    private QuestionVO question;

    @Schema(description ="阅读时间")
    private Date readTime;

    @Schema(description ="创建时间")
    private Date createdTime;

    @Schema(description ="更新时间")
    private Date updatedTime;
}
