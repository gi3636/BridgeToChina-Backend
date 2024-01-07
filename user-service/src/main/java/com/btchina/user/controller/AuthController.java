package com.btchina.user.controller;


import com.btchina.core.api.CommonResult;
import com.btchina.user.model.form.LoginForm;
import com.btchina.user.model.form.RegisterForm;
import com.btchina.feign.model.user.vo.UserVO;
import com.btchina.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@Api(tags = "授权模块")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public CommonResult<UserVO> login(@Validated @RequestBody LoginForm loginForm) {
        UserVO userVo = userService.login(loginForm.getUsername(), loginForm.getPassword());
        return CommonResult.success(userVo);
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<ObjectUtils.Null> register(@Validated @RequestBody RegisterForm registerForm) {
        Boolean registerSuccess = userService.register(registerForm);
        if (!registerSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

}

