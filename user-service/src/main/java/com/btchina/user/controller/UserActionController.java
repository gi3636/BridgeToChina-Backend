package com.btchina.user.controller;


import com.btchina.core.api.CommonResult;
import com.btchina.model.form.user.UserActionForm;
import com.btchina.user.service.UserActionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户动态表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@RestController
@Tag(name = "用户动态模块")
@RequestMapping("/user/userAction")
public class UserActionController {

    @Autowired
    private UserActionService userActionService;
    @ApiOperation(value = "添加用户动态")
    @PostMapping("/add")
    public CommonResult<Void> add(@RequestBody UserActionForm userActionForm) {
        Boolean isSuccess = userActionService.add(userActionForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "删除用户动态")
    @PostMapping("/delete")
    public CommonResult<Void> delete(@RequestBody UserActionForm userActionForm) {
        Boolean isSuccess = userActionService.delete(userActionForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

}

