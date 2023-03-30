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

/**
 * <p>
 * 会话详情表
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@Getter
@Setter
@TableName("tbl_dialog")
@ApiModel(value = "Dialog对象", description = "会话详情表")
public class Dialog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("最后接收消息ID")
    private String lastMsgId;

    @ApiModelProperty("聊天类型 1是私聊 2是群聊")
    private Integer chatType;

    @ApiModelProperty("消息类型 1是文本 2是图片 ")
    private Integer messageType;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("未读消息数")
    private Integer unreadCount;

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
