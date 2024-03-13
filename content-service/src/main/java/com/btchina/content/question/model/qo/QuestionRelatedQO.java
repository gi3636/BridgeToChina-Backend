package com.btchina.content.question.model.qo;

import com.btchina.core.api.PageQueryParam;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Schema(description = "相关问题搜索")
public class QuestionRelatedQO extends PageQueryParam {
    @Schema(description ="关键字", required = true)
    public String keyword;
}
