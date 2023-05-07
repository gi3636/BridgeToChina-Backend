package com.btchina.admin.handler;

import com.alibaba.fastjson.JSON;
import com.btchina.admin.utils.WebUtils;

import com.btchina.core.api.CommonResult;
import com.btchina.core.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("认证失败了");
        String json = JSON.toJSONString(CommonResult.failed(ResultCode.UNAUTHORIZED));
        WebUtils.renderString(response, json);
    }
}
