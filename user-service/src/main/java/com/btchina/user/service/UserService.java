package com.btchina.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.user.entity.User;
import com.btchina.user.model.form.RegisterForm;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-01-30
 */
public interface UserService extends IService<User> {

    Boolean register(RegisterForm registerForm);
}
