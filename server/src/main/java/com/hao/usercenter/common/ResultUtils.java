package com.hao.usercenter.common;

/**
 * 响应工具类：统一构建 {@link BaseResponse}
 */
public class ResultUtils {

    private ResultUtils() {
    }

    /**
     * 成功，携带数据
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMsg(), "", data);
    }

    /**
     * 成功，无数据
     */
    public static <T> BaseResponse<T> success() {
        return success(null);
    }

    /**
     * 失败（使用 ErrorCode 默认提示）
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMsg(), errorCode.getDesc());
    }

    /**
     * 失败（覆盖提示信息），如：error(ErrorCode.PARAMS_ERROR, "账号长度4~16位")
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode, String msg) {
        return error(errorCode.getCode(), msg, errorCode.getDesc());
    }

    /**
     * 失败（完全自定义）
     */
    public static <T> BaseResponse<T> error(int code, String msg, String desc) {
        return new BaseResponse<>(code, msg, desc, null);
    }
}
