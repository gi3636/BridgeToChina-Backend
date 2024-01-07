package com.btchina.content.comment.model.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentUpdateQO {

    @ApiModelProperty("评论id")
    private Long id;

    @ApiModelProperty("内容")
    private String content;

}
