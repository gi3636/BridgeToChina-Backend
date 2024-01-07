package com.btchina.feign.clients;

import com.btchina.core.api.CommonResult;
import com.btchina.feign.model.userAction.qo.UserActionForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "content-service")
public interface UserActionClient {

    @PostMapping("/user/userAction/add")
    CommonResult<Void> addUserAction(@RequestBody UserActionForm userActionForm);
    @PostMapping("/user/userAction/delete")
    CommonResult<Void> deleteUserAction(@RequestBody UserActionForm userActionForm);
}
