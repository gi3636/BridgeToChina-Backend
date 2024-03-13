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
 * 用户采用问题关系表
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
@Getter
@Setter
@TableName("tbl_answer_user_use")
@Schema(name = "AnswerUserUse对象", description = "用户采用问题关系表")
public class AnswerUserUse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description ="id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(description ="回答id")
    private Long answerId;

    @Schema(description ="用户Id")
    private Long userId;

    @Schema(description ="状态 0是取消 1是采用")
    private Integer status;

    @Schema(description ="创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @Schema(description ="更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;


}
