//package com.btchina.admin.config;
//
//
//import com.btchina.admin.filter.JwtAuthenticationTokenFilter;
//import com.btchina.admin.handler.AccessDeniedHandlerImpl;
//import com.btchina.admin.handler.AuthenticationEntryPointImpl;
//import com.btchina.redis.service.RedisService;
//import com.btchina.core.util.JwtTokenUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)// 开启权限注解
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private RedisService redisService;
//
//    @Autowired
//    private AccessDeniedHandlerImpl accessDeniedHandler;
//    @Autowired
//    private AuthenticationEntryPointImpl authenticationEntryPoint;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                //关闭csrf
//                .csrf().disable()
//                //不通过Session获取SecurityContext
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                // 除上面外的所有请求全部需要鉴权认证
//                // 对于登录接口 允许匿名访问
//                .authorizeRequests()
//                .antMatchers("/admin/auth/**", "/v3/**", "/api/**").permitAll()
//                .anyRequest().authenticated();
//
//        //把token校验过滤器添加到过滤器链中
//        http.addFilterBefore(new JwtAuthenticationTokenFilter(jwtTokenUtil, redisService), UsernamePasswordAuthenticationFilter.class);
//
//        //配置异常处理器
//        http.exceptionHandling()
//                //配置认证失败处理器
//                .authenticationEntryPoint(authenticationEntryPoint)
//                .accessDeniedHandler(accessDeniedHandler);
//
//        //允许跨域
//        http.cors();
//
//        // 将登录框关闭
//        http.formLogin().disable();
//    }
//
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        // 设置不拦截规则
//        web.ignoring().antMatchers("/admin/auth/login", "/v3/**", "/api/**");
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//}
