package com.hao.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应格式
 * <p>
 * code：业务状态码，200 表示成功，其余为错误码；<br>
 * msg：提示信息（用户可见）；<br>
 * desc：错误描述（开发排查用，可为空）；<br>
 * data：响应数据（失败时通常为 null）。
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -5460937766424968236L;

    /**
     * 业务状态码
     */
    private int code;

    /**
     * 提示信息（用户可见）
     */
    private String msg;

    /**
     * 错误描述（开发排查用，可为空）
     */
    private String desc;

    /**
     * 响应数据
     */
    private T data;

    public BaseResponse(int code, String msg, String desc, T data) {
        this.code = code;
        this.msg = msg;
        this.desc = desc;
        this.data = data;
    }
}
