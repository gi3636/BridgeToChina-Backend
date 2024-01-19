package com.btchina.core.exception;

import com.btchina.core.api.ResultCode;
import com.btchina.core.i18n.MessageSourceUtil;
import com.btchina.core.util.SpringUtil;
import lombok.Data;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/23/2021 2:53 PM
 */
@Data
public class GlobalException extends RuntimeException {

    private long code;

    private String message;

    private MessageSourceUtil messageSourceUtil;
    public GlobalException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = messageSourceUtil.getMessage(resultCode.getKey());
    }

    private GlobalException(long code, String key) {
        this.code = code;
        MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
        this.message = messageSource.getMessage(key, new String[]{"validation"}, LocaleContextHolder.getLocale());
    }


    public GlobalException(String message) {
        super(message);
        this.message = message;
    }


    public static GlobalException from(ResultCode resultCode) {
        GlobalException globalException = new GlobalException(resultCode.getCode(), resultCode.getKey());
        return globalException;
    }

    public static GlobalException from(String msg) {
        GlobalException globalException = new GlobalException(msg);
        return globalException;
    }
}
