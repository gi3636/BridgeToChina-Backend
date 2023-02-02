package com.btchina.core.exception;

import com.btchina.core.api.ResultCode;
import lombok.Data;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/23/2021 2:53 PM
 */
@Data
public class GlobalException extends RuntimeException {

    private long code;

    private String message;

    public GlobalException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    private GlobalException(long code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }


    public GlobalException(String message) {
        super(message);
        this.message = message;
    }


    public static GlobalException from(ResultCode resultCode) {
        GlobalException globalException = new GlobalException(resultCode.getCode(), resultCode.getMessage());
        return globalException;
    }

    public static GlobalException from(String msg) {
        GlobalException globalException = new GlobalException(msg);
        return globalException;
    }
}
