package com.btchina.feign.clients;

import com.btchina.core.api.CommonResult;
import com.btchina.entity.User;
import com.btchina.model.vo.user.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name="user-service")
public interface UserClient {

    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);


    @PostMapping("/user/findByIds")
    Map<Long,UserVO> findByIds(@RequestBody List<Long> ids);

}
