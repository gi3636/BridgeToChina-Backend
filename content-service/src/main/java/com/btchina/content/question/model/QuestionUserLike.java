package com.btchina.content.question.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 问题点赞表
 * </p>
 *
 * @author franky
 * @since 2023-02-09
 */
@Getter
@Setter
@TableName("tbl_question_user_like")
@ApiModel(value = "QuestionUserLike对象", description = "问题点赞表")
public class QuestionUserLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("问题id")
    private Long questionId;

    @ApiModelProperty("点赞状态 0是取消 1是点赞")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;


}
