package com.hao.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hao.usercenter.common.BaseResponse;
import com.hao.usercenter.common.ErrorCode;
import com.hao.usercenter.common.ResultUtils;
import com.hao.usercenter.constant.UserConstant;
import com.hao.usercenter.exception.BusinessException;
import com.hao.usercenter.model.User;
import com.hao.usercenter.model.request.LoginReqeust;
import com.hao.usercenter.model.request.RegisterRequest;
import com.hao.usercenter.model.vo.LoginVO;
import com.hao.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/user"))
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> register(@RequestBody RegisterRequest registerRequest) {
        return ResultUtils.success(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public BaseResponse<LoginVO> login(@RequestBody LoginReqeust loginReqeust, HttpServletRequest request) {
        return ResultUtils.success(userService.login(loginReqeust, request));
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request) {
        return ResultUtils.success(userService.logout(request));
    }

    @GetMapping("/current")
    public BaseResponse<User> currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(UserConstant.USER_LOGIN_STATE);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        return ResultUtils.success(userService.getSafetyUser(userService.getById(currentUser.getId())));
    }

    @GetMapping("/list")
    public BaseResponse<List<User>> list(String username, HttpServletRequest request) {
        // 管理员可操作
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        QueryWrapper<User> qw = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            qw.like("username", username);
        }
        List<User> list = userService.list(qw);
        return ResultUtils.success(list.stream()
                .map(u -> userService.getSafetyUser(u))
                .collect(Collectors.toList()));
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody Long id, HttpServletRequest request) {
        // 管理员可操作
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "id不能为空");
        }
        return ResultUtils.success(userService.removeById(id));
    }


    /**
     * 是否管理员
     * @param request
     * @return
     */
    private Boolean isAdmin(HttpServletRequest request) {
        // 管理员可操作
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user == null) {
            return false;
        }
        return user.getRole() == UserConstant.ROLE_ADMIN;
    }
}
