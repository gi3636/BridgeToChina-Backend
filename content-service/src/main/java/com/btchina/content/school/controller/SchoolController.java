package com.btchina.content.school.controller;


import com.btchina.content.question.model.Question;
import com.btchina.core.api.CommonResult;
import com.btchina.core.i18n.MessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * <p>
 * 学校表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2024-01-15
 */
@RestController
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MessageSourceUtil messageSourceUtil;

    @GetMapping("/test")
    public CommonResult<String> test() {
        System.out.println(LocaleContextHolder.getLocale());
        String result = messageSource.getMessage("test",
                new String[]{"message"}, Locale.SIMPLIFIED_CHINESE);
        System.out.println("result: " + result);
        System.out.println("test: " + messageSourceUtil.getMessage("test"));
        return CommonResult.success(result);
    }

}

