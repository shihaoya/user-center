package com.hao.usercenter.service;

import com.hao.usercenter.model.request.RegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 用户服务测试
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void register_success() {
        RegisterRequest dto = new RegisterRequest();
        dto.setAccount("testuser001");
        dto.setPassword("Abc12345");
        dto.setConfirmPassword("Abc12345");

        long id = userService.register(dto);
        Assertions.assertTrue(id > 0);
    }

    @Test
    void register_blankFields_returnsMinusOne() {
        RegisterRequest dto = new RegisterRequest();
        dto.setAccount("");
        dto.setPassword("");
        dto.setConfirmPassword("");

        long result = userService.register(dto);
        Assertions.assertEquals(-1L, result);
    }

    @Test
    void register_accountTooShort_returnsMinusOne() {
        RegisterRequest dto = new RegisterRequest();
        dto.setAccount("abc");
        dto.setPassword("Abc12345");
        dto.setConfirmPassword("Abc12345");

        long result = userService.register(dto);
        Assertions.assertEquals(-1L, result);
    }

    @Test
    void register_accountTooLong_returnsMinusOne() {
        RegisterRequest dto = new RegisterRequest();
        dto.setAccount("abcdefghijklmnopq");
        dto.setPassword("Abc12345");
        dto.setConfirmPassword("Abc12345");

        long result = userService.register(dto);
        Assertions.assertEquals(-1L, result);
    }

    @Test
    void register_accountWithSpecialChars_returnsMinusOne() {
        RegisterRequest dto = new RegisterRequest();
        dto.setAccount("test@user");
        dto.setPassword("Abc12345");
        dto.setConfirmPassword("Abc12345");

        long result = userService.register(dto);
        Assertions.assertEquals(-1L, result);
    }

    @Test
    void register_passwordTooShort_returnsMinusOne() {
        RegisterRequest dto = new RegisterRequest();
        dto.setAccount("testuser002");
        dto.setPassword("Abc1234");
        dto.setConfirmPassword("Abc1234");

        long result = userService.register(dto);
        Assertions.assertEquals(-1L, result);
    }

    @Test
    void register_passwordMismatch_returnsMinusOne() {
        RegisterRequest dto = new RegisterRequest();
        dto.setAccount("testuser003");
        dto.setPassword("Abc12345");
        dto.setConfirmPassword("Abc12346");

        long result = userService.register(dto);
        Assertions.assertEquals(-1L, result);
    }

    @Test
    void register_duplicateAccount_returnsMinusOne() {
        // 先注册一个用户
        RegisterRequest first = new RegisterRequest();
        first.setAccount("dupuser001");
        first.setPassword("Abc12345");
        first.setConfirmPassword("Abc12345");
        userService.register(first);

        // 再次用相同账号注册
        RegisterRequest second = new RegisterRequest();
        second.setAccount("dupuser001");
        second.setPassword("Abc12345");
        second.setConfirmPassword("Abc12345");

        long result = userService.register(second);
        Assertions.assertEquals(-1L, result);
    }
}