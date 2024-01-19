package com.btchina.content.school.controller;


import com.btchina.content.school.business.SchoolBusiness;
import com.btchina.content.school.model.qo.SchoolListQO;
import com.btchina.content.school.model.vo.SchoolVO;
import com.btchina.core.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/school")
public class SchoolController {


    @Autowired
    private SchoolBusiness schoolBusiness;

    @PostMapping("/list")
    public CommonResult<List<SchoolVO>> list(@RequestBody SchoolListQO schoolListQO) {
        System.out.println("schoolListQO = " + schoolListQO);
        return CommonResult.success(schoolBusiness.list(schoolListQO));
    }

}

