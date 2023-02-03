package com.btchina.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SessionUtil {
    @Autowired
    HttpServletRequest request;

    public void setAttribute(String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    public Object getAttribute(String key) {
        return request.getSession().getAttribute(key);
    }

    public Long getUserId() {
        return (Long) request.getSession().getAttribute("id");
    }
}
