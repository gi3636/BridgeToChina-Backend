package com.btchina.message.netty.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataContent implements Serializable {
    private Integer action;
    private ChatMsg chatMsg;
    private String extend;
}

