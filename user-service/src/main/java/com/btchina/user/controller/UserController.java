package com.btchina.user.controller;


import com.btchina.core.api.CommonResult;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.core.util.AuthHelper;
import com.btchina.redis.service.RedisService;
import com.btchina.user.entity.User;
import com.btchina.user.model.form.EditUserForm;
import com.btchina.user.model.form.GetUserForm;
import com.btchina.user.model.vo.UserVO;
import com.btchina.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
@Api(tags = "用户模块")
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "根据id查询用户信息")
    @GetMapping("{id}")
    public User findById(@PathVariable("id") Long id) {
        User user = userService.getBaseMapper().selectById(id);
        user.setPassword(null);
        return user;
    }

    @ApiOperation(value = "根据id查询用户信息")
    @PostMapping("/getDetail")
    public CommonResult<UserVO> getDetail(@RequestBody GetUserForm getUserForm) {
        UserVO userVO = userService.getDetail(getUserForm.getId());
        return CommonResult.success(userVO);
    }


    @ApiOperation(value = "根据id列表查询用户信息")
    @PostMapping("/findByIds")
    public Map<Long, UserVO> findByIds(@RequestBody List<Long> ids) {
        return userService.findByIds(ids);
    }


    @ApiOperation(value = "编辑用户信息")
    @PostMapping("/edit")
    public CommonResult<Void> edit(@RequestBody EditUserForm editUserForm) {
        Long id = AuthHelper.getUserId();
        Boolean isSuccess = userService.edit(id, editUserForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


}

