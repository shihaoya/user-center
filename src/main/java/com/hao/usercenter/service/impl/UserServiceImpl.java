package com.hao.usercenter.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.hao.usercenter.model.User;
import com.hao.usercenter.service.UserService;
import com.hao.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author qq163
* @description 针对表【user】的数据库操作Service实现
* @createDate 2026-08-26 21:26:35
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




