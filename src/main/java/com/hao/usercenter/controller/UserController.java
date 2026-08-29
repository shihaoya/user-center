package com.hao.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hao.usercenter.contant.UserConstant;
import com.hao.usercenter.model.User;
import com.hao.usercenter.model.dto.LoginDTO;
import com.hao.usercenter.model.dto.RegisterDTO;
import com.hao.usercenter.model.vo.LoginVO;
import com.hao.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/user"))
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Long register(@RequestBody RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    @PostMapping("/login")
    public LoginVO login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        return userService.login(loginDTO, request);
    }

    @GetMapping("/list")
    public List<User> list(String username, HttpServletRequest request) {
        // 管理员可操作
        if (isAdmin(request)) {
            return new ArrayList<>();
        }
        QueryWrapper<User> qw = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            qw.like("username", username);
        }
        List<User> list = userService.list(qw);
        return list.stream()
                .map(u -> userService.getSafetyUser(u))
                .collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public Boolean delete(@RequestBody Long id, HttpServletRequest request) {
        // 管理员可操作
        if (isAdmin(request)) {
            return false;
        }
        if (id == null) {
            return false;
        }
        return userService.removeById(id);
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
