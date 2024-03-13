package com.btchina.notification.controller;


import com.btchina.core.api.CommonResult;
import com.btchina.core.api.PageResult;
import com.btchina.core.util.AuthHelper;
import com.btchina.notification.model.qo.NotifyQueryForm;
import com.btchina.notification.model.qo.NotifyReadAllForm;
import com.btchina.notification.model.qo.NotifyReadForm;
import com.btchina.notification.model.vo.NotifyVO;
import com.btchina.feign.model.message.qo.NotifyAddForm;
import com.btchina.notification.service.NotifyService;
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
 * 消息通知表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@RestController
@Tag(name = "消息通知模块")
@RequestMapping("/message/notify")
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    @Parameter(name = "添加通知")
    @PostMapping("/add")
    public Boolean add(@Validated @RequestBody NotifyAddForm notifyAddForm) {
        return notifyService.add(notifyAddForm);
    }

    @Parameter(name ="查询通知列表")
    @PostMapping("/list")
    public CommonResult<PageResult<NotifyVO>> list(@Validated @RequestBody NotifyQueryForm notifyQueryForm) {
        Long userId = AuthHelper.getUserId();
        PageResult<NotifyVO> result = notifyService.list(userId, notifyQueryForm);
        return CommonResult.success(result);
    }

    @Parameter(name ="已读通知")
    @PostMapping("/read")
    public CommonResult<Void> read(@Validated @RequestBody NotifyReadForm notifyReadForm) {
        Long userId = AuthHelper.getUserId();
        Boolean result = notifyService.read(userId, notifyReadForm);
        if (!result) {
            return CommonResult.failed("已读失败");
        }
        return CommonResult.success(null);
    }


    @Parameter(name ="已读全部通知")
    @PostMapping("/readAll")
    public CommonResult<Void> read(@Validated @RequestBody NotifyReadAllForm notifyReadAllForm) {
        Long userId = AuthHelper.getUserId();
        Boolean result = notifyService.readAll(userId, notifyReadAllForm);
        if (!result) {
            return CommonResult.failed("已读失败");
        }
        return CommonResult.success(null);
    }
}

