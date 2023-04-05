package com.btchina.feign.clients;

import com.btchina.model.form.message.NotifyAddForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "message-service")
public interface MessageClient {

    @PostMapping("/message/notify/add")
     Boolean pushMessage(@Validated @RequestBody NotifyAddForm notifyAddForm);
}
