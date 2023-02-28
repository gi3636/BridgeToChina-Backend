package com.btchina.feign.clients;

import com.btchina.entity.Answer;
import com.btchina.entity.User;
import com.btchina.model.vo.answer.AnswerVO;
import com.btchina.model.vo.user.UserVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "answer-service")
public interface AnswerClient {

    @PostMapping("/answer/findVOById")
    AnswerVO findVOById(@RequestBody Long answerId);

    @PostMapping("/answer/findById")
    Answer findById(@RequestBody Long answerId);

    @PostMapping("/answer/decrease/comment/count")
    void decreaseCommentCount(@Validated @RequestBody Long answerId);

    @PostMapping("/answer/increase/comment/count")
    void increaseCommentCount(@Validated @RequestBody Long answerId);
}

