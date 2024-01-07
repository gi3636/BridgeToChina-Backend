package com.btchina.content.comment.feign.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentAddQO {

    @ApiModelProperty("回答id")
    @NotNull(message = "回答id不能为空")
    private Long answerId;

    @ApiModelProperty("回复的评论id")
    private Long parentId;

    @ApiModelProperty("回复的对象id")
    private Long toUserId;

    @ApiModelProperty("评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;

}
