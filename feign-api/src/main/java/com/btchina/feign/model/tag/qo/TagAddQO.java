package com.btchina.feign.model.tag.qo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class TagAddQO {
    @Schema(description ="问题id或者用户id")
    private Long id;

    @Schema(description ="标签内容")
    private List<String> tags;

}
