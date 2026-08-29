package com.hao.usercenter.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.hao.usercenter.model.User;
import com.hao.usercenter.model.dto.LoginDTO;
import com.hao.usercenter.model.dto.RegisterDTO;
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
     * @param registerDTO 注册参数
     * @return 用户id
     */
    long register(RegisterDTO registerDTO);

    /**
     * 登录
     *
     * @param loginDTO 登录参数
     * @param request
     * @return 用户信息
     */
    LoginVO login(LoginDTO loginDTO, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    User getSafetyUser(User user);
}
