package com.btchina.comment.controller;


import com.btchina.comment.model.form.AddCommentForm;
import com.btchina.comment.service.CommentService;
import com.btchina.core.api.CommonResult;
import com.btchina.core.util.AuthHelper;
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
 * 评论表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
@Api(tags = "评论模块")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "添加评论")
    @PostMapping("/add")
    public CommonResult<Void> addComment(@Validated @RequestBody AddCommentForm addCommentForm) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = commentService.addComment(addCommentForm, userId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


}

