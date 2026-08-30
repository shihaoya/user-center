package com.hao.usercenter.execption;

import com.hao.usercenter.common.ErrorCode;
import lombok.Getter;

/**
 * 自定义异常类
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 4397542353332770313L;
    private final int  code;
    private final String desc;

    public BusinessException(String msg, int code, String desc) {
        super(msg);
        this.code = code;
        this.desc = desc;
    }
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.desc = errorCode.getDesc();
    }

    public BusinessException(ErrorCode errorCode, String desc) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.desc = desc;
    }

}
