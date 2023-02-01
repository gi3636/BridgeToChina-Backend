package com.btchina.user.controller;


import com.btchina.redis.service.RedisService;
import com.btchina.user.entity.User;
import com.btchina.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/user/")
@RefreshScope
public class UserController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    @GetMapping("test")
    public User queryById() {
        User user = userService.getBaseMapper().selectById(1);
        redisService.set("test", user);
        User user1 = (User) redisService.get("test");
        System.out.println("test" + user1);
        return user;
    }
}

