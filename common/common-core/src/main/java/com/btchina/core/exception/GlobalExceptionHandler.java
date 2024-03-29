package com.btchina.core.exception;


import com.btchina.core.api.CommonResult;
import com.btchina.core.i18n.MessageSourceUtil;
import com.btchina.core.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import java.util.stream.Collectors;

/**
 * @description: 全局异常处理
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 指定出现什么异常执行这个方法
     */
    //@ExceptionHandler(Exception.class)
    //public ResultVo error(Exception e) {
    //    e.printStackTrace();
    //    return ResultVo.error().msg("服务器又耍流氓了..");
    //}

    /**
     * 异常信息兜底
     */
    @ExceptionHandler(Exception.class)
    public Object doException(GlobalException e) {
        if (e instanceof GlobalException) {
            GlobalException ge = (GlobalException) e;
            log.error("系统故障：{}", ge.getMessage());
            ge.printStackTrace();
            System.out.println("系统故障：" + ge.getMessage());
            return CommonResult.failed(ge.getCode(), ge.getMessage(), false);
        }
        return null;
    }


    /**
     * 自定义异常处理
     *
     * @param e
     * @return ResultVo
     */
    @ExceptionHandler(GlobalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult error(GlobalException e) {
        log.error("异常信息：{} {}", e.getCode(), e.getMessage(), e);
        return CommonResult.failed(e.getCode(), (e.getMessage()), false);
    }

    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<CommonResult<String>> handleValidatedException(Exception e) {
        String message = null;
        MessageSourceUtil messageSourceUtil = SpringUtil.getBean(MessageSourceUtil.class);
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            message = ex.getBindingResult().getAllErrors().stream()
                    .map(objectError -> messageSourceUtil.getMessage(objectError.getDefaultMessage()))
                    .collect(Collectors.joining("; ")
                    );
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ex = (ConstraintViolationException) e;
            message = ex.getConstraintViolations().stream()
                    .map(objectError -> messageSourceUtil.getMessage(objectError.getMessage()))
                    .collect(Collectors.joining("; ")
                    );
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            message = ex.getAllErrors().stream()
                    .map(objectError -> messageSourceUtil.getMessage(objectError.getDefaultMessage()))
                    .collect(Collectors.joining("; ")
                    );
        }
        CommonResult<String> resp = CommonResult.failed(message);

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }


}
