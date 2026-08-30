package com.hao.usercenter.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.hao.usercenter.model.User;
import com.hao.usercenter.model.request.LoginReqeust;
import com.hao.usercenter.model.request.RegisterRequest;
import com.hao.usercenter.model.vo.LoginVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author qq163
* @description 针对表【user】的数据库操作Service
* @createDate 2026-08-26 21:26:35
*/
public interface UserService extends IService<User> {

    /**
     * 注册
     * @param registerRequest 注册参数
     * @return 用户id
     */
    Long register(RegisterRequest registerRequest);

    /**
     * 登录
     *
     * @param loginReqeust 登录参数
     * @param request
     * @return 用户信息
     */
    LoginVO login(LoginReqeust loginReqeust, HttpServletRequest request);

    /**
     * 登出
     *
     * @param request
     * @return
     */
    Boolean logout(HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    User getSafetyUser(User user);

}
