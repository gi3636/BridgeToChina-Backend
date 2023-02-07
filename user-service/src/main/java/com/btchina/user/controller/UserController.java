package com.btchina.user.controller;


import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.redis.service.RedisService;
import com.btchina.user.entity.User;
import com.btchina.user.service.UserService;
import com.btchina.core.util.AuthHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-01-30
 */
@RestController
@Slf4j
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    @GetMapping("test")
    public User test() {
        User user = userService.getBaseMapper().selectById(1);
        redisService.set("test", user);
        User user1 = (User) redisService.get("test");
        System.out.println("test" + user1);
        return user;
    }

    @GetMapping("{id}")
    public User findById(@PathVariable("id") Long id) {
        User user = userService.getBaseMapper().selectById(id);
        redisService.set("test", user);
        Long userId = AuthHelper.getUserId();
        String username = AuthHelper.getUsername();
        log.info("userId:{}", userId);
        log.info("username:{}", username);
        //User user1 = (User) redisService.get("test");
        //System.out.println("test" + user1.toString());
        if (user == null) {
            throw new GlobalException(ResultCode.COMMENT_NOT_EXIST);
        }
        return user;
    }

}

