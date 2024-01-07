package com.btchina.content.question.controller;


import com.btchina.content.question.model.qo.AnswerUseQO;
import com.btchina.content.question.service.AnswerService;
import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import com.btchina.core.util.AuthHelper;
import com.btchina.content.question.model.qo.AnswerAddQO;
import com.btchina.content.question.model.qo.AnswerQueryQO;
import com.btchina.content.question.model.qo.AnswerUpdateQO;
import com.btchina.feign.model.question.vo.AnswerVO;
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
    public CommonResult<Void> addAnswer(@Validated @RequestBody AnswerAddQO answerAddQO) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = answerService.addAnswer(answerAddQO, userId);
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
    public CommonResult<Void> updateAnswer(@Validated @RequestBody AnswerUpdateQO answerUpdateQO) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = answerService.updateAnswer(answerUpdateQO, userId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "查询回答")
    @PostMapping("/list")
    public CommonResult<PageResult<AnswerVO>> getAnswerList(@Validated @RequestBody AnswerQueryQO answerQueryQO) {
        Long userId = AuthHelper.getUserId();
        PageResult<AnswerVO> voList = answerService.queryAnswer(answerQueryQO, userId);
        return CommonResult.success(voList);
    }


    @ApiOperation(value = "采用回答")
    @PostMapping("/use")
    public CommonResult<Void> use(@Validated @RequestBody AnswerUseQO answerUseQO) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = answerService.use(answerUseQO.getId(), answerUseQO.getStatus(), userId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


    @ApiOperation(value = "增加评论数")
    @PostMapping("/increase/comment/count")
    public void addCommentCount(@Validated @RequestBody Long answerId) {
        answerService.increaseCommentCount(answerId);
    }

    @ApiOperation(value = "减少评论数")
    @PostMapping("/decrease/comment/count")
    public void decreaseCommentCount(@Validated @RequestBody Long answerId) {
        answerService.decreaseCommentCount(answerId);
    }


    @ApiOperation(value = "获取回答详情")
    @PostMapping("/findById")
    public AnswerVO findById(@Validated @RequestBody Long answerId) {
        return answerService.findVOById(answerId);
    }

    @ApiOperation(value = "获取回答详情")
    @PostMapping("/findVOById")
    public AnswerVO findVOById(@Validated @RequestBody Long answerId) {
        return answerService.findVOById(answerId);
    }
}

