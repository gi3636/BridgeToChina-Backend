package com.btchina.content.question.model.qo;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "问题点赞参数对象")
public class QuestionLikeQO {

    @NotNull(message = "问题id不能为空")
    @Schema(description ="问题id")
    private Long questionId;
}
