package com.btchina.core.exception;


import com.btchina.core.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        log.error("系统故障：{}", e.getMessage());
        e.printStackTrace();
        return CommonResult.failed(e.getCode(), (e.getMessage()));
    }


    /**
     * 自定义异常处理
     *
     * @param e
     * @return ResultVo
     */
    @ExceptionHandler(GlobalException.class)
    public CommonResult error(GlobalException e) {
        log.error("异常信息：{} {}", e.getCode(), e.getMessage(), e);
        return CommonResult.failed(e.getCode(), (e.getMessage()));
    }
}
