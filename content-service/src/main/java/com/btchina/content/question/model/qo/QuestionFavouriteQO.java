package com.btchina.content.question.model.qo;



import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Tag(name = "问题收藏表单")
public class QuestionFavouriteQO {

    @NotNull(message = "问题id不能为空")
    @Schema(description = "问题id", required = true)
    private Long questionId;

    @NotNull(message = "收藏状态不能为空")
    @Schema(description = "收藏状态 1 收藏 0 取消收藏", required = true)
    private Integer status;
}
