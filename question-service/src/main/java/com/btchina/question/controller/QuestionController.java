package com.btchina.question.controller;


import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import com.btchina.core.util.AuthHelper;
import com.btchina.feign.clients.UserClient;
import com.btchina.feign.pojo.User;
import com.btchina.question.entity.Question;
import com.btchina.question.model.doc.QuestionDoc;
import com.btchina.question.model.form.AddQuestionForm;
import com.btchina.question.model.form.QuestionLikeForm;
import com.btchina.question.model.form.QuestionQueryForm;
import com.btchina.question.model.vo.QuestionVO;
import com.btchina.question.service.QuestionService;
import com.btchina.question.service.QuestionUserLikeService;
import com.btchina.redis.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 问答表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-02-01
 */
@RestController
@Api(tags = "问答模块")
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private UserClient userClient;


    @Autowired
    private RedisService redisService;
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionUserLikeService questionUserLikeService;

    @ApiOperation(value = "问答测试")
    @GetMapping("{id}")
    public User queryById(@PathVariable("id") Integer id) {
        Question question = questionService.getBaseMapper().selectById(id);
        redisService.set("test1", question);
        Question question1 = (Question) redisService.get("test1");
        System.out.println("test1" + question1.toString());
        User user= userClient.findById(1L);
        System.out.println("user " + user.toString());
        return user;
    }


    @ApiOperation(value = "发布问题")
    @PostMapping("/add")
    public CommonResult<Void> addQuestion(@Validated @RequestBody AddQuestionForm addQuestionForm) {
        Long selfId = AuthHelper.getUserId();
        Boolean isSuccess = questionService.addQuestion(addQuestionForm, selfId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


    @ApiOperation(value = "删除问题")
    @PostMapping("/delete")
    public CommonResult<Void> deleteQuestion(@RequestBody DeleteForm deleteForm) {
        Long selfId = AuthHelper.getUserId();
        Boolean isSuccess = questionService.deleteQuestion(deleteForm.getId(), selfId);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


    @ApiOperation(value = "获取问题列表")
    @PostMapping("/list")
    public CommonResult<PageResult<QuestionVO>> getList(@Validated @RequestBody QuestionQueryForm questionQueryForm) {
        Long selfId = AuthHelper.getUserId();
        PageResult<QuestionVO> result = questionService.queryQuestion(questionQueryForm, selfId);
        return CommonResult.success(result);
    }


    @ApiOperation(value = "获取es问题列表")
    @PostMapping("/list/es")
    public CommonResult<SearchHits<QuestionDoc>> getEsList(@Validated @RequestBody QuestionQueryForm questionQueryForm) {
        Long selfId = AuthHelper.getUserId();
        SearchHits<QuestionDoc> result = questionService.queryEsQuestion(questionQueryForm, selfId);
        return CommonResult.success(result);
    }

    @ApiOperation(value = "获取测试列表")
    @GetMapping("/list/test")
    public CommonResult<List<Question>> getAllList() {
        Long selfId = AuthHelper.getUserId();
        List<Question> result = questionService.query().list();
        return CommonResult.success(result);
    }


    @ApiOperation(value = "获取问题详情")
    @GetMapping("/detail/{id}")
    public CommonResult<Question> getQuestionDetails(@PathVariable("id") Long id) {
        Long selfId = AuthHelper.getUserId();
        Question result = questionService.getById(id);
        return CommonResult.success(result);
    }


    @ApiOperation(value = "点赞")
    @PostMapping("/like")
    public CommonResult<Void> like(@Validated @RequestBody QuestionLikeForm questionLikeForm) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = questionUserLikeService.like(questionLikeForm.getQuestionId(), userId);
        if (!isSuccess) {
            return CommonResult.failed("点赞失败");
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "点赞")
    @PostMapping("/unlike")
    public CommonResult<Void> unlike(@Validated @RequestBody QuestionLikeForm questionLikeForm) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess = questionUserLikeService.unlike(questionLikeForm.getQuestionId(), userId);
        if (!isSuccess) {
            return CommonResult.failed("取消赞失败");
        }
        return CommonResult.success(null);
    }
}

