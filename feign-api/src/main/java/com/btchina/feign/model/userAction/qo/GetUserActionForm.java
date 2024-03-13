package com.btchina.feign.model.userAction.qo;

import com.btchina.core.api.PageQueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

@Data
@ToString
public class GetUserActionForm extends PageQueryParam {

    @Schema(description = "用户id",required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;
}
