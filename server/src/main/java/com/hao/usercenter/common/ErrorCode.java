package com.hao.usercenter.common;


import lombok.Getter;

/**
 * 错误码
 */
@Getter
public enum ErrorCode {

    PARAMS_ERROR(40000, "请求参数错误！", ""),
    NULL_ERROR(40001, "数据为空！", ""),
    NO_AUTH(40100, "暂无权限！", ""),
    SYSTEM_EXCEPTION(50000, "系统异常！", "");


    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String msg;

    /**
     * 错误描述
     */
    private final String desc;

    ErrorCode(int code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }

}
