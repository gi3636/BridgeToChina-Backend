package com.btchina.content.comment.model.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "评论点赞参数对象")
public class CommentLikeQO {
    @NotNull(message = "评论id不能为空")
    @ApiModelProperty("评论id")
    private Long id;

    @NotNull(message = "回答id不能为空")
    @ApiModelProperty("回答id")
    private Long answerId;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty("状态 0是取消 1是点赞")
    private Integer status;

}
