package com.btchina.content.question.model;

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
 * 问题收藏表
 * </p>
 *
 * @author franky
 * @since 2023-02-09
 */
@Getter
@Setter
@TableName("tbl_question_user_favorite")
@Schema(name = "QuestionUserFavorite对象", description = "问题收藏表")
public class QuestionUserFavorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description ="用户id")
    private Long userId;

    @Schema(description ="问题Id")
    private Long questionId;

    @Schema(description ="收藏状态 0是取消 1是收藏")
    private Integer status;

    @Schema(description ="创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @Schema(description ="更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;


}
