package com.btchina.notification.model.qo;

import com.btchina.core.api.PageQueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotifyReadForm  {
    @Schema(description = "id",required = true)
    Long id;
}
