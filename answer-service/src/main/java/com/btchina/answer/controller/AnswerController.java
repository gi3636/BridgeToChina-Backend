package com.btchina.answer.controller;


import com.btchina.answer.service.AnswerService;
import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import com.btchina.core.util.AuthHelper;
import com.btchina.model.form.answer.AddAnswerForm;
import com.btchina.model.form.answer.QueryAnswerForm;
import com.btchina.model.form.answer.UpdateAnswerForm;
import com.btchina.model.vo.answer.AnswerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 回答表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
@Api(tags = "回答模块")
@RestController
@RequestMapping("/answer")
public class AnswerController {


    @Autowired
    private AnswerService answerService;


    @ApiOperation(value = "添加回答")
    @PostMapping("/add")
    public CommonResult<Void> addAnswer(@Validated @RequestBody AddAnswerForm addAnswerForm) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = answerService.addAnswer(addAnswerForm, userId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "删除回答")
    @PostMapping("/delete")
    public CommonResult<Void> delAnswer(@Validated @RequestBody DeleteForm deleteForm) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = answerService.delAnswer(deleteForm, userId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "修改回答")
    @PostMapping("/update")
    public CommonResult<Void> updateAnswer(@Validated @RequestBody UpdateAnswerForm updateAnswerForm) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = answerService.updateAnswer(updateAnswerForm, userId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "查询回答")
    @PostMapping("/list")
    public CommonResult<PageResult<AnswerVO>> getAnswerList(@Validated @RequestBody QueryAnswerForm queryAnswerForm) {
       PageResult<AnswerVO> voList = answerService.queryAnswer(queryAnswerForm);
        return CommonResult.success(voList);
    }
}

