package com.btchina.content.comment.model.qo;

import com.btchina.core.api.PageQueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class CommentQueryQO extends PageQueryParam {
    @NotNull(message = "回答id不能为空")
    @Schema(description ="回答id")
    private Long answerId;

}
