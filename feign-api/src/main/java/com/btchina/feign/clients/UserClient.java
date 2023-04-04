package com.btchina.feign.clients;

import com.btchina.core.api.CommonResult;
import com.btchina.entity.User;
import com.btchina.model.form.user.UserActionForm;
import com.btchina.model.vo.user.UserVO;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);


    @PostMapping("/user/findByIds")
    Map<Long, UserVO> findByIds(@RequestBody List<Long> ids);


    @PostMapping("/user/userAction/add")
    CommonResult<Void> addUserAction(@RequestBody UserActionForm userActionForm);
    @PostMapping("/user/userAction/delete")
    CommonResult<Void> deleteUserAction(@RequestBody UserActionForm userActionForm);
}
