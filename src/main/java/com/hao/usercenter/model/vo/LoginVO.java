package com.hao.usercenter.model.vo;

import com.hao.usercenter.model.User;
import lombok.Data;

/**
 * 登录返回
 */
@Data
public class LoginVO {

    /**
     * 登录令牌
     */
    private String token;

    /**
     * 用户信息
     */
    private User userInfo;
}
