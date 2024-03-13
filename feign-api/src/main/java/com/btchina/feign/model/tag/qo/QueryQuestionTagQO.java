package com.btchina.feign.model.tag.qo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class QueryQuestionTagQO {
    @Schema(description ="问题id或者用户id")
    private List<Long> ids;
}
