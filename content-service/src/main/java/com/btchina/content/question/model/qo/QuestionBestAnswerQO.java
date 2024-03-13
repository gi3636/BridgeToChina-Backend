package com.btchina.content.question.model.qo;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Tag(name = "问题设置最佳答案")
public class QuestionBestAnswerQO {

    @NotNull(message = "问题id不能为空")
    @Schema(description = "问题id", required = true)
    private Long questionId;
    @NotNull(message = "回答id不能为空")
    @Schema(description = "回答id", required = true)
    private Long answerId;
}
