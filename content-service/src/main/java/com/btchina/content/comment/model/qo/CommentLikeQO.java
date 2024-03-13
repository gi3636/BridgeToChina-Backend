package com.btchina.content.comment.model.qo;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "评论点赞参数对象")
public class CommentLikeQO {
    @NotNull(message = "评论id不能为空")
    @Schema(description ="评论id")
    private Long id;

    @NotNull(message = "回答id不能为空")
    @Schema(description ="回答id")
    private Long answerId;

    @NotNull(message = "状态不能为空")
    @Schema(description ="状态 0是取消 1是点赞")
    private Integer status;

}
