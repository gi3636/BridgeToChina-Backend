package com.btchina.user.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.user.entity.User;
import com.btchina.user.manager.UserManager;
import com.btchina.user.mapper.UserMapper;
import com.btchina.user.model.form.RegisterForm;
import com.btchina.user.model.vo.UserVO;
import com.btchina.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.user.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-01-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserManager userManager;


    @Override
    public Boolean register(RegisterForm registerForm) {
        //判断两个密码是否一致
        if (!registerForm.getPassword().equals(registerForm.getConfirmPassword())) {
            throw GlobalException.from(ResultCode.PASSWORD_NOT_SAME);
        }
        //查询是否有用户
        User existUser = userManager.getAndSetCacheByUsername(registerForm.getUsername());
        if (existUser != null) {
            throw GlobalException.from(ResultCode.USER_EXITS);
        }
        User user = new User();
        //将密码进行加密操作
        String encodePassword = DigestUtil.md5Hex(registerForm.getPassword());
        //设置用户信息
        user.setUsername(registerForm.getUsername());
        user.setMobile(registerForm.getUsername());
        user.setPassword(encodePassword);
        user.setAvatar("https://avatars1.githubusercontent.com/u/" + (int) (Math.random() * 1000));
        user.setNickname("吃饱没事干");
        //保存用户信息
        int result = baseMapper.insert(user);
        return result > 0;
    }

    @Override
    public UserVO login(String username, String password) {
        //获取缓存信息
        User user = userManager.getUser(username);
        if (user == null) {
            throw GlobalException.from(ResultCode.USER_NOT_FOUND);
        }
        if (!DigestUtil.md5Hex(password).equals(user.getPassword())) {
            throw GlobalException.from(ResultCode.PASSWORD_WRONG);
        }
        UserVO userVo = new UserVO();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        userVo.setMobile(user.getMobile());
        userVo.setNickname(user.getNickname());
        userVo.setAvatar(user.getAvatar());
        userVo.setSex(user.getSex());
        userVo.setBirthday(user.getBirthday());
        userVo.setCountry(user.getCountry());
        userVo.setCity(user.getCity());
        userVo.setDescription(user.getDescription());
        userVo.setCover(user.getCover());
        userVo.setToken(jwtTokenUtil.generateToken(user));
        return userVo;
    }
}
