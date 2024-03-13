package com.btchina.admin.controller;


import com.btchina.admin.model.form.SystemUserAddForm;
import com.btchina.admin.model.vo.SysUserVO;
import com.btchina.admin.service.SysUserService;
import com.btchina.core.api.CommonResult;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户账户 前端控制器
 * </p>
 *
 * @author franky
 * @since 2022-10-12
 */
@Tag(name = "管理后台管理员模块")
@RestController
@RequestMapping("/admin/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

   @Parameter(name = "添加管理员")
    @PostMapping("/add")
    public CommonResult<SysUserVO> add(@Validated @RequestBody SystemUserAddForm systemUserAddForm) {
        SysUserVO userVo = sysUserService.add(systemUserAddForm);
        return CommonResult.success(userVo);
    }

}

