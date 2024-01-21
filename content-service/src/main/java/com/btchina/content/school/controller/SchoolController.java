package com.btchina.content.school.controller;


import com.btchina.content.school.business.SchoolBusiness;
import com.btchina.content.school.model.qo.SchoolQO;
import com.btchina.content.school.model.vo.SchoolVO;
import com.btchina.core.api.CommonResult;
import com.btchina.core.api.ResultCode;
import com.btchina.core.api.valid.ValidGroup;
import com.btchina.core.api.valid.ValidatedList;
import com.btchina.core.exception.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 学校表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2024-01-15
 */
@Validated
@RestController
@RequestMapping("/school")
public class SchoolController {


    @Autowired
    private SchoolBusiness schoolBusiness;

    @PostMapping("/list")
    public CommonResult<List<SchoolVO>> list(@RequestBody @Validated(value = ValidGroup.Query.class) SchoolQO schoolQO) {
        System.out.println("schoolListQO = " + schoolQO);
        return CommonResult.success(schoolBusiness.list(schoolQO));
    }


    @PostMapping("/add")
    public CommonResult<?> add(@RequestBody @Validated(value = ValidGroup.Create.class)  ValidatedList<SchoolQO> schoolQOList) {
        boolean result = schoolBusiness.add(schoolQOList);
        return result ? CommonResult.success(result) : CommonResult.failed();
    }


    @PostMapping("/edit")
    public CommonResult<?> edit(@RequestBody @Validated(value = ValidGroup.Update.class) ValidatedList<SchoolQO> schoolQOList) {
        boolean result = schoolBusiness.edit(schoolQOList);
        return result ? CommonResult.success(result) : CommonResult.failed();
    }
}

