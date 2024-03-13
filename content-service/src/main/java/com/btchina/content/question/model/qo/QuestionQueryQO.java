package com.btchina.content.question.model.qo;

import com.btchina.core.api.PageQueryParam;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Schema(description = "问题查询参数对象")
public class QuestionQueryQO extends PageQueryParam {

    @Schema(description = "查询类型 1:热门 2:最新 3:推荐 4:我的 5:关注", required = true, example = "1")
    private Integer type;

    @Schema(description = "查询时间")
    private Long date;
}
