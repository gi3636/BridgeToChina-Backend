package com.btchina.question.model.form;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
@ApiModel(description = "问题查询参数对象")
public class QuestionQueryForm extends PageQueryParam {

    @ApiModelProperty(value = "查询类型 1:热门 2:最新 3:推荐 4:我的 5:关注", required = true, example = "1")
    private Integer type;

    @ApiModelProperty(value = "查询时间")
    private Long date;
}
