package com.hao.usercenter.service;

import com.hao.usercenter.model.User;
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
    public void testAddUser() {
        User user = new User();
        user.setUsername("test");
        user.setAccount("test");
        user.setAvatar("");
        user.setGender(1);
        user.setPassword("test");
        user.setPhone("");
        user.setEmail("");
        user.setStatus(1);
        boolean saved = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(saved);
    }
}