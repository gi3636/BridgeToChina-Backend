package com.btchina.question.controller;


import com.btchina.core.api.CommonResult;
import com.btchina.core.util.AuthHelper;
import com.btchina.feign.clients.UserClient;
import com.btchina.feign.pojo.User;
import com.btchina.question.entity.Question;
import com.btchina.question.model.doc.QuestionDoc;
import com.btchina.question.model.form.AddQuestionForm;
import com.btchina.question.model.form.QuestionQueryForm;
import com.btchina.question.service.QuestionService;
import com.btchina.redis.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        Boolean isSuccess = questionService.addQuestion(addQuestionForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        // 标题 内容 标签
        return CommonResult.success(null);
    }


    @ApiOperation(value = "获取问题列表")
    @PostMapping("/list")
    public CommonResult<SearchHits<QuestionDoc>> getList(@Validated @RequestBody QuestionQueryForm questionQueryForm) {
        Long selfId = AuthHelper.getUserId();
        SearchHits<QuestionDoc> result = questionService.queryQuestion(questionQueryForm, selfId);
        return CommonResult.success(result);
    }

}

