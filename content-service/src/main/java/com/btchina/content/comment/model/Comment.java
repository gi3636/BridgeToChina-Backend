package com.btchina.content.comment.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
@Getter
@Setter
@TableName("tbl_comment")
@ToString
@Tag(name = "Comment对象", description = "评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description ="评论id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description ="回答id")
    private Long answerId;

    @Schema(description ="用户id")
    private Long userId;

    @Schema(description ="回复的评论id")
    private Long parentId;

    @Schema(description ="回复的对象id")
    private Long toUserId;

    @Schema(description ="评论内容")
    private String content;

    @Schema(description ="评论点赞数")
    private Integer likeCount;

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
