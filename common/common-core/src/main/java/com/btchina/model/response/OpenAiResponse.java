package com.btchina.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OpenAiResponse {
    private String id;
    private String model;
    private Long created;
    private String prompt;
    private List<ResponseData> choices;
    private String object;

}
