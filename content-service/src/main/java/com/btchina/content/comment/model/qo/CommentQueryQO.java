package com.btchina.content.comment.model.qo;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentQueryQO extends PageQueryParam {
    @NotNull(message = "回答id不能为空")
    @ApiModelProperty("回答id")
    private Long answerId;

}
