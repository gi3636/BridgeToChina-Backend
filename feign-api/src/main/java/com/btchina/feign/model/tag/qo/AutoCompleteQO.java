package com.btchina.feign.model.tag.qo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AutoCompleteQO {
    @Schema(description = "关键字", required = true)
    private String keyword;
}
