package com.btchina.content.comment.controller;


import com.btchina.content.comment.model.qo.CommentAddQO;
import com.btchina.content.comment.model.qo.CommentLikeQO;
import com.btchina.content.comment.model.qo.CommentQueryQO;
import com.btchina.content.comment.model.qo.CommentUpdateQO;
import com.btchina.content.comment.model.vo.CommentVO;
import com.btchina.content.comment.service.CommentService;
import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import com.btchina.core.util.AuthHelper;

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
 * 评论表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
@Tag(name = "评论模块")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Parameter(name = "添加评论")
    @PostMapping("/add")
    public CommonResult<Void> addComment(@Validated @RequestBody CommentAddQO commentAddQO) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = commentService.addComment(commentAddQO, userId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


    @Parameter(name = "删除评论")
    @PostMapping("/del")
    public CommonResult<Void> delComment(@Validated @RequestBody DeleteForm deleteForm) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = commentService.delComment(deleteForm, userId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


   @Parameter(name = "修改评论")
    @PostMapping("/update")
    public CommonResult<Void> updateComment(@Validated @RequestBody CommentUpdateQO commentUpdateQO) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = commentService.updateComment(commentUpdateQO, userId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


   @Parameter(name = "查询回答评论")
    @PostMapping("/list")
    public CommonResult<PageResult<CommentVO>> list(@Validated @RequestBody CommentQueryQO commentQueryQO) {
        Long userId = AuthHelper.getUserId();
        PageResult<CommentVO> result = commentService.queryCommentList(commentQueryQO, userId);
        return CommonResult.success(result);
    }


   @Parameter(name = "点赞评论")
    @PostMapping("/like")
    public CommonResult<Void> like(@Validated @RequestBody CommentLikeQO commentLikeQO) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = commentService.like(commentLikeQO, userId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }
}

