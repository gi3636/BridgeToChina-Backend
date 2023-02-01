package com.btchina.question.controller;


import com.btchina.question.entity.Question;
import com.btchina.question.service.QuestionService;
import com.btchina.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * <p>
 * 问答表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-02-01
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private QuestionService questionService;
    @GetMapping("{id}")
    public Question queryById(@PathVariable("id") Integer id) {
        Question question = questionService.getBaseMapper().selectById(id);
        redisService.set("test1", question);
        Question question1 = (Question) redisService.get("test1");
        System.out.println("test1" + question1.toString());
        return question;
    }
}

