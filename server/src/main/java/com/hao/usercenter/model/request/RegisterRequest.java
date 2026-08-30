package com.hao.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册前端传参
 */
@Data
public class RegisterRequest implements Serializable {

    private static final long serialVersionUID = 4947073067756228150L;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String confirmPassword;

    /**
     * 知识星球编号
     */
    private String planetCode;
}
