package com.btchina.notification.model.qo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotifyReadAllForm {
    @Schema(description = "多个通知的id",required = true)
    private Long[] ids;
}
