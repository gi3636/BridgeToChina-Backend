package com.btchina.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.user.entity.User;
import com.btchina.user.model.form.EditUserForm;
import com.btchina.user.model.form.RegisterForm;
import com.btchina.feign.model.user.vo.UserVO;

import java.util.List;
import java.util.Map;

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

    UserVO login(String username, String password);

    Map<Long, UserVO> findByIds(List<Long> ids);

    UserVO getDetail(Long id);

    Boolean edit(Long id, EditUserForm editUserForm);
}
