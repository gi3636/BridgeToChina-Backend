package com.btchina.user.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.core.contants.RedisConstant;
import com.btchina.core.exception.GlobalException;
import com.btchina.redis.service.RedisService;
import com.btchina.user.entity.User;
import com.btchina.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lintiancheng
 */
@Component
public class UserManager extends ServiceImpl<UserMapper, User> {

    private final String userKey = RedisConstant.REDIS_DATABASE + ":" + RedisConstant.REDIS_KEY_USER + ":";

    @Autowired
    private RedisService redisService;

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    public User getAndSetCacheByUsername(String username) {
        if (username == null) {
            throw GlobalException.from("username 不能为空");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User existUser = baseMapper.selectOne(wrapper);
        if (existUser != null) {
            String key = userKey + existUser.getUsername();
            redisService.set(key, existUser, RedisConstant.REDIS_EXPIRE);
        }
        return existUser;
    }


    public User getUser(String username) {
        if (username == null) {
            throw GlobalException.from("username 不能为空");
        }
        User user = this.getAndSetCacheByUsername(username);
        return user;
    }

    public void updateUserCache(User user) {
        if (user == null) {
            throw GlobalException.from("user 不能为空");
        }
        getUser(user.getUsername());
        String key = userKey + user.getUsername();
        redisService.set(key, user, RedisConstant.REDIS_EXPIRE);
    }
}
