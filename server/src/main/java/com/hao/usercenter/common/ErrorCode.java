package com.hao.usercenter.common;

import lombok.Getter;

/**
 * 错误码
 */
@Getter
public enum ErrorCode {

    SUCCESS(200, "操作成功", ""),
    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "数据为空", ""),
    NOT_LOGIN(40101, "未登录", ""),
    NO_AUTH(40100, "暂无权限", ""),
    NOT_FOUND(40400, "请求的数据不存在", ""),
    SYSTEM_EXCEPTION(50000, "系统异常", ""),
    OPERATION_ERROR(50001, "操作失败", "");

    /**
     * 业务状态码
     */
    private final int code;

    /**
     * 提示信息（用户可见）
     */
    private final String msg;

    /**
     * 错误描述（开发排查用，可为空）
     */
    private final String desc;

    ErrorCode(int code, String msg, String desc) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
    }
}
