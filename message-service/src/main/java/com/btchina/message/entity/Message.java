package com.btchina.message.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@Getter
@Setter
@TableName("tbl_message")
@ToString
@ApiModel(value = "Message对象", description = "消息表")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("会话ID")
    private Long dialogId;

    @ApiModelProperty("发送者ID")
    private Long senderId;

    @ApiModelProperty("接收者ID")
    private Long receiverId;

    @ApiModelProperty("消息ID(前端消息ID)")
    private String msgId;


    @ApiModelProperty("聊天类型 1是私聊 2是群聊")
    private Integer chatType;

    @ApiModelProperty("消息类型 1是文本 2是图片 ")
    private Integer messageType;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("是否已读 1是已读 , 0 是未读")
    private Boolean isRead;

    @ApiModelProperty("是否签收 1是已签收 , 0 是未签收")
    private Boolean signed;

    @ApiModelProperty("扩展")
    private String extend;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @ApiModelProperty("是否删除;1是删除，0是不删除")
    @TableLogic
    private Boolean deleted;


}
