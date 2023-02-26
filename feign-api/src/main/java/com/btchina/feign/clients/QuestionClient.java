package com.btchina.feign.clients;

import com.btchina.entity.Question;
import com.btchina.entity.User;
import com.btchina.model.vo.user.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "question-service")
public interface QuestionClient {


    @GetMapping("/question/findById/{id}")
    Question findById(@PathVariable("id") Long id);
    @PostMapping("/question/increase/answer/count")
    void increaseAnswerCount(@RequestBody Long questionId);

    @PostMapping("/question/decrease/answer/count")
    void decreaseAnswerCount(@RequestBody Long questionId);
}
