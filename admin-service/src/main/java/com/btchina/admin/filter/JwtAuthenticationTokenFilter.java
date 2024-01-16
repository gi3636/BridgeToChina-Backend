package com.btchina.admin.filter;


import com.btchina.admin.constant.SysUserConstant;
import com.btchina.admin.entity.LoginUser;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.redis.service.RedisService;
import com.btchina.core.util.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private JwtTokenUtil jwtTokenUtil;
    private RedisService redisService;

    public JwtAuthenticationTokenFilter(JwtTokenUtil jwtTokenUtil, RedisService redisService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.redisService = redisService;
    }

    private static final String loginKey = SysUserConstant.LOGIN_KEY;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("进入JwtAuthenticationTokenFilter过滤器");
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //验证token
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("token: " + token);
        //解析token
        Long id = jwtTokenUtil.getIdFromToken(token);
        //从redis获取用户信息
        //SysUser sysUser = (SysUser) redisService.get(loginKey + id);

        //LoginUser loginUser = new LoginUser(sysUser);
        LoginUser loginUser = (LoginUser) redisService.get(loginKey + id);
        if (Objects.isNull(loginUser)) {
            throw GlobalException.from(ResultCode.USER_NOT_LOGIN);
        }
        //Todo 获取权限信息
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        //存入securityContextHolder
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
