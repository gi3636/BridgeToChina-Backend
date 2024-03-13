package com.btchina.admin.config;

import com.btchina.admin.filter.JwtAuthenticationTokenFilter;
import com.btchina.admin.handler.AccessDeniedHandlerImpl;
import com.btchina.admin.handler.AuthenticationEntryPointImpl;
import com.btchina.core.util.JwtTokenUtil;
import com.btchina.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)// 开启权限注解
public class SecurityConfiguration {


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //.authorizeHttpRequests((authz) -> authz
                //        .anyRequest().authenticated()
                //)
                .httpBasic(withDefaults())
                .cors(withDefaults())
                .formLogin(withDefaults())
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                )
                .csrf(AbstractHttpConfigurer::disable
                )
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new JwtAuthenticationTokenFilter(jwtTokenUtil, redisService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/admin/auth/**", "/v3/**", "/api/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }


}
