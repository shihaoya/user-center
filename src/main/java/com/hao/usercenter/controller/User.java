package com.hao.usercenter.controller;

import com.hao.usercenter.model.dto.LoginDTO;
import com.hao.usercenter.model.dto.RegisterDTO;
import com.hao.usercenter.model.vo.LoginVO;
import com.hao.usercenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(("/user"))
public class User {

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
}
