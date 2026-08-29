package com.hao.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录前端传参
 */
@Data
public class LoginReqeust implements Serializable {

    private static final long serialVersionUID = -5613039428747569636L;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

}
