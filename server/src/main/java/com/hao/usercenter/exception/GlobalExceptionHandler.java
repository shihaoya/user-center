package com.hao.usercenter.exception;

import com.hao.usercenter.common.BaseResponse;
import com.hao.usercenter.common.ErrorCode;
import com.hao.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器：所有异常统一转换为 {@link BaseResponse} 返回
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 业务异常：按业务码原样返回，msg 展示给用户，desc 供排查
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> handleBusinessException(BusinessException e) {
        log.error("业务异常：code={}, msg={}, desc={}", e.getCode(), e.getMessage(), e.getDesc(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDesc());
    }

    /**
     * 请求体解析失败（参数缺失 / JSON 格式错误等）
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResponse<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("请求体解析失败：{}", e.getMessage());
        return ResultUtils.error(ErrorCode.PARAMS_ERROR, "请求体缺失或格式错误");
    }

    /**
     * 兜底运行时异常：不对外暴露内部细节
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常：", e);
        return ResultUtils.error(ErrorCode.SYSTEM_EXCEPTION);
    }

    /**
     * 兜底所有未处理异常
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> handleException(Exception e) {
        log.error("系统异常：", e);
        return ResultUtils.error(ErrorCode.SYSTEM_EXCEPTION);
    }
}
