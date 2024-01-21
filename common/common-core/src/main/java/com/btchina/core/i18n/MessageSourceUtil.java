package com.btchina.core.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceUtil {

    @Autowired
    MessageSource messageSource;

    public String getMessage(String key, Object... vals) {
        String message = messageSource.getMessage(key,  new String[]{"validation","message"}, LocaleContextHolder.getLocale());
        return String.format(message, vals);
    }

    public String getMessage(String key, String file, Object... vals) {
        String message = messageSource.getMessage(key, new String[]{file}, LocaleContextHolder.getLocale());
        return String.format(message, vals);
    }

}
