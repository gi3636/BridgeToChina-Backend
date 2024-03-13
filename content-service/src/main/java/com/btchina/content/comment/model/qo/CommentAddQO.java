package com.btchina.content.comment.model.qo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class CommentAddQO {

    @Schema(description ="回答id")
    @NotNull(message = "回答id不能为空")
    private Long answerId;

    @Schema(description ="回复的评论id")
    private Long parentId;

    @Schema(description ="回复的对象id")
    private Long toUserId;

    @Schema(description ="评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;

}
