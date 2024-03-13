package com.btchina.feign.model.tag.qo;

import com.btchina.core.api.PageQueryParam;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Tag(name = "问题查询参数对象")
public class TagQueryQO extends PageQueryParam {


    @Schema(description = "keyword")
    private String keyword;
}
