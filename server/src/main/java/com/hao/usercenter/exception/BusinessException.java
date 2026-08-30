package com.hao.usercenter.exception;

import com.hao.usercenter.common.ErrorCode;
import lombok.Getter;

/**
 * 自定义业务异常
 * <p>
 * code：业务状态码，与 {@link ErrorCode} 对应；<br>
 * msg：展示给用户看的提示信息（即 getMessage()）；<br>
 * desc：补充描述，仅供开发排查，可为空。
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 4397542353332770313L;

    /**
     * 业务状态码
     */
    private final int code;

    /**
     * 补充描述（开发排查用，可为空）
     */
    private final String desc;

    public BusinessException(int code, String msg, String desc) {
        super(msg);
        this.code = code;
        this.desc = desc;
    }

    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMsg(), errorCode.getDesc());
    }

    /**
     * 使用具体提示覆盖 ErrorCode 默认 msg，如：
     * <pre>throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度4~16位");</pre>
     */
    public BusinessException(ErrorCode errorCode, String msg) {
        this(errorCode.getCode(), msg, errorCode.getDesc());
    }
}
