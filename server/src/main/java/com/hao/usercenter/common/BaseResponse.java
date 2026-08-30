package com.hao.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应格式
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -5460937766424968236L;
    private int code;
    private String msg;
    private String desc;
    private T data;


    public BaseResponse(ErrorCode errorCode, String msg) {
        this.code = errorCode.getCode();
        this.msg = msg;
    }
    public BaseResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }
    public BaseResponse(int code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }
    public BaseResponse(int code, String msg, String desc, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.desc = desc;
    }

}
