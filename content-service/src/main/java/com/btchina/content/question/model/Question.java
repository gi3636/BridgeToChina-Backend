package com.btchina.content.question.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * 问答表
 * </p>
 *
 * @author franky
 * @since 2023-02-01
 */

@Data
@ToString
@TableName("tbl_question")
@Schema(name = "Question对象", description = "问答表")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description ="Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description ="用户Id")
    private Long userId;

    @Schema(description ="最佳回答Id")
    private Long bestAnswerId;

    @Schema(description ="标题")
    private String title;

    @Schema(description ="内容")
    private String content;

    @Schema(description ="收藏数")
    private Integer favoriteCount;

    @Schema(description ="点赞数")
    private Integer likeCount;

    @Schema(description ="浏览数")
    private Integer viewCount;

    @Schema(description ="回答数")
    private Integer answerCount;

    @Schema(description ="图片,多个图片用逗号分隔")
    private String images;

    @Schema(description ="是否公开 1是公开，0是个人可见")
    private Boolean isPublic;

    @Schema(description ="审核状态 0是未审核 1是审核通过 2是审核不通过")
    private Integer status;

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
