package com.btchina.model.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseData {
    private String text;
    private String index;
    private String logprobs;
    private String finish_reason;
}
