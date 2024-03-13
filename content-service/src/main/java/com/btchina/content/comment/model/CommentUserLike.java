package com.btchina.content.comment.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 评论点赞数表
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
@Getter
@Setter
@TableName("tbl_comment_user_like")
@Schema(name = "CommentUserLike对象", description = "评论点赞数表")
public class CommentUserLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description ="id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description ="用户id")
    private Long userId;

    @Schema(description ="评论id")
    private Long commentId;

    @Schema(description ="回答id")
    private Long answerId;

    @Schema(description ="点赞状态 0是取消 1是点赞")
    private Integer status;

    @Schema(description ="创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @Schema(description ="更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;


}
