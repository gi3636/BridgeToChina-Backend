package com.btchina.notification.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 消息通知表
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@Getter
@Setter
@TableName("tbl_notify")
@ToString
@Schema(name = "Notify对象", description = "消息通知表")
public class Notify implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description ="id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description ="发送者id, 0是系统发送")
    private Long senderId;

    @Schema(description ="接收者id")
    private Long receiverId;

    @Schema(description ="对象id")
    private Long objectId;

    @Schema(description ="操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注")
    private Integer actionType;

    @Schema(description ="消息模板id")
    private Integer templateId;

    @Schema(description ="渠道类型 1 站内信 2短信 3邮箱")
    private Integer channelType;

    @Schema(description ="对象类型 1 问题 2 用户")
    private Integer objectType;

    @Schema(description ="是否已读  1已读 0未读 ")
    private Boolean isRead;

    @Schema(description ="阅读时间")
    private Date readTime;

    @Schema(description ="创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @Schema(description ="更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @Schema(description ="是否删除;1是删除，0是不删除")
    @TableLogic
    private Boolean deleted;


}
