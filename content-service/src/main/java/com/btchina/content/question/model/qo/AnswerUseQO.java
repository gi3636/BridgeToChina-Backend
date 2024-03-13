package com.btchina.content.question.model.qo;



import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "回答采用参数对象")
public class AnswerUseQO {
    @NotNull(message = "回答id不能为空")
    @Schema(description ="回答id")
    private Long id;

    @NotNull(message = "状态不能为空")
    @Schema(description ="状态 0是取消 1是采用")
    private Integer status;

}
