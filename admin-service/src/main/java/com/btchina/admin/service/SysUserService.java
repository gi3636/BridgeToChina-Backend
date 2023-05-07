package com.btchina.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.admin.entity.SysUser;
import com.btchina.admin.model.form.SystemUserAddForm;
import com.btchina.admin.model.vo.SysUserVO;

/**
 * <p>
 * 用户账户 服务类
 * </p>
 *
 * @author franky
 * @since 2022-10-12
 */
public interface SysUserService extends IService<SysUser> {

    SysUserVO login(String username, String password);

    Boolean logout();

    SysUserVO add(SystemUserAddForm systemUserAddForm);
}
