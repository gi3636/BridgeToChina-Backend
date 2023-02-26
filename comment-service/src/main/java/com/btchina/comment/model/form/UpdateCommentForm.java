package com.btchina.comment.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateCommentForm {

    @ApiModelProperty("评论id")
    private Long id;

    @ApiModelProperty("内容")
    private String content;

}
