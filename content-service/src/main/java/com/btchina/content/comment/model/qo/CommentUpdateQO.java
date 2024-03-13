package com.btchina.content.comment.model.qo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentUpdateQO {

    @Schema(description ="评论id")
    private Long id;

    @Schema(description ="内容")
    private String content;

}
