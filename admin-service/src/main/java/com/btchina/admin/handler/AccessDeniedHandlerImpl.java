package com.btchina.admin.handler;

import com.alibaba.fastjson.JSON;
import com.btchina.admin.utils.WebUtils;

import com.btchina.core.api.CommonResult;
import com.btchina.core.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("认证失败了");
        String json = JSON.toJSONString(CommonResult.failed(ResultCode.FORBIDDEN));
        WebUtils.renderString(response, json);
    }
}
