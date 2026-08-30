package com.hao.usercenter.common;

/**
 * 响应工具类
 */
public class ResultUtils {

    /**
     * 成功响应
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, "操作成功！", "", data);
    }

    /**
     * 失败响应
     * @param errorCode
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败响应
     * @param errorCode
     * @param desc
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(ErrorCode errorCode, String desc) {
        return new BaseResponse<>(errorCode, desc);
    }

    /**
     * 失败响应
     * @param code
     * @param msg
     * @param desc
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> error(int code, String msg, String desc) {
        return new BaseResponse<>(code, msg, desc);
    }

}
