package com.btchina.message.controller;


import com.btchina.core.api.CommonResult;
import com.btchina.core.api.PageResult;
import com.btchina.core.util.AuthHelper;
import com.btchina.message.model.form.MessageQueryForm;
import com.btchina.message.model.form.MessageReadForm;
import com.btchina.message.model.vo.MessageVO;
import com.btchina.message.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@RestController
@Api(tags = "消息模块")
@RequestMapping("/message/")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @ApiOperation("获取消息列表")
    @PostMapping("list")
    public CommonResult<PageResult<MessageVO>> list(@Validated @RequestBody MessageQueryForm messageQueryForm) {
        Long userId = AuthHelper.getUserId();
        PageResult<MessageVO> messageVOList = messageService.query(userId, messageQueryForm);
        return CommonResult.success(messageVOList);
    }

    @ApiOperation("已读消息")
    @PostMapping("read")
    public CommonResult<Void> read(@Validated @RequestBody MessageReadForm messageReadForm) {
        Long userId = AuthHelper.getUserId();
        Boolean isSuccess =  messageService.read(userId, messageReadForm.getMsgId());
        if (!isSuccess) {
            return CommonResult.failed("消息已读");
        }
        return CommonResult.success(null);
    }

}

