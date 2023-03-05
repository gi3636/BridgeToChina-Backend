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
import com.btchina.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        user.setNickname(registerForm.getUsername());
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
        UserVO userVo = UserVO.convert(user);
        userVo.setToken(jwtTokenUtil.generateToken(user.getId(),user.getUsername()));
        return userVo;
    }

    @Override
    public Map<Long, UserVO> findByIds(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            return null;
        }
        List<User> userList = this.baseMapper.selectBatchIds(ids);
        Map<Long, UserVO> userVOMap = new HashMap<>();
        for (User user : userList) {
            UserVO userVO = UserVO.convert(user);
            userVOMap.put(user.getId(), userVO);
        }
        return userVOMap;
    }
}
