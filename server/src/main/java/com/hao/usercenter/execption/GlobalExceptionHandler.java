package com.hao.usercenter.execption;

import com.hao.usercenter.common.BaseResponse;
import com.hao.usercenter.common.ErrorCode;
import com.hao.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 统一处理系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse handleException(BusinessException e) {
        log.error("业务异常：{}", e);
        return ResultUtils.error(e.getCode(), e.getDesc(), "");
    }

    /**
     * 隔离内部异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse handleException(RuntimeException e) {
        log.error("运行时异常：{}", e);
        return ResultUtils.error(ErrorCode.SYSTEM_EXCEPTION);
    }
}
