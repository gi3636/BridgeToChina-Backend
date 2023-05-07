package com.btchina.admin.controller;



import com.btchina.admin.model.form.LoginForm;
import com.btchina.admin.model.vo.SysUserVO;
import com.btchina.admin.service.SysUserService;
import com.btchina.core.api.CommonResult;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2022-08-29
 */

@Api(tags = "管理后台授权模块")
@RestController
@RequestMapping("/admin/auth")
public class AuthAdminController {

    @Autowired
    private SysUserService sysUserService;


    @PreAuthorize("hasAuthority('test1')")
    @GetMapping("/hello")
    @ApiOperation(value = "测试")
    public CommonResult<Void> hello() {
        return CommonResult.success(null);
    }


    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public CommonResult<SysUserVO> login(@Validated @RequestBody LoginForm loginForm) {
        SysUserVO userVo = sysUserService.login(loginForm.getUsername(), loginForm.getPassword());
        return CommonResult.success(userVo);
    }


    @ApiOperation(value = "用户登出")
    @GetMapping("logout")
    public CommonResult<SysUserVO> logout() {
        Boolean isSuccess = sysUserService.logout();
        return CommonResult.success(null);
    }


}

