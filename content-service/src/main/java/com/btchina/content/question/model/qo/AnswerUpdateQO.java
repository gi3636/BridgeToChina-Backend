package com.btchina.content.question.model.qo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AnswerUpdateQO {

    @Schema(description ="回答id")
    private Long id;

    @Schema(description ="回答内容")
    private String content;

}
