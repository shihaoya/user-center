package com.hao.usercenter.constant;

/**
 * 用户常量
 */
public interface UserConstant {
    // 密码盐
    String SALT = "HAO";
    // 用户登录状态
    String USER_LOGIN_STATE = "userLoginState";

    // 管理员角色
    int ROLE_ADMIN = 1;
    // 普通用户角色
    int ROLE_USER = 2;
}
