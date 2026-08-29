package com.hao.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.hao.usercenter.contant.UserConstant;
import com.hao.usercenter.model.User;
import com.hao.usercenter.model.request.LoginReqeust;
import com.hao.usercenter.model.request.RegisterRequest;
import com.hao.usercenter.model.vo.LoginVO;
import com.hao.usercenter.service.UserService;
import com.hao.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author qq163
* @description 针对表【user】的数据库操作Service实现
* @createDate 2026-08-26 21:26:35
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public long register(RegisterRequest registerRequest) {
        String account = registerRequest.getAccount();
        String password = registerRequest.getPassword();
        String confirmPassword = registerRequest.getConfirmPassword();

        // 字段不能为空
        if (StringUtils.isAllBlank(account, password, confirmPassword)) {
            return -1;
        }
        // 账号4-16位
        if (account.length() < 4 || account.length() > 16) {
            return -1;
        }
        // 密码8-16位
        if (password.length() < 8 || password.length() > 16 || confirmPassword.length() < 8 || confirmPassword.length() > 16) {
            return -1;
        }
        // 密码不一致
        if (!password.equals(confirmPassword)) {
            return -1;
        }
        // 不要特殊字符
        String validPattern = "^[a-zA-Z0-9]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(account);
        if (!matcher.matches()) {
            return -1;
        }
        // 账号不能重复
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("account", account);
        long count = userMapper.selectCount(qw);
        if (count > 0) {
            return -1;
        }

        // 加密
        String encryptPasswd = DigestUtils.md5DigestAsHex((UserConstant.SALT + password).getBytes());

        // 插入数据
        User user = new User();
        user.setAccount(account);
        user.setPassword(encryptPasswd);
        return userMapper.insert(user);
    }

    @Override
    public LoginVO login(LoginReqeust loginReqeust, HttpServletRequest request) {
        String account = loginReqeust.getAccount();
        String password = loginReqeust.getPassword();

        // 字段不能为空
        if (StringUtils.isAllBlank(account, password)) {
            return null;
        }
        // 账号4-16位
        if (account.length() < 4 || account.length() > 16) {
            return null;
        }
        // 密码8-16位
        if (password.length() < 8 || password.length() > 16) {
            return null;
        }
        // 不要特殊字符
        String validPattern = "^[a-zA-Z0-9]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(account);
        if (!matcher.matches()) {
            return null;
        }
        // 查询用户
        // 加密
        String encryptPasswd = DigestUtils.md5DigestAsHex((UserConstant.SALT + password).getBytes());
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("account", account);
        qw.eq("password", encryptPasswd);
        User user = userMapper.selectOne(qw);
        if (user == null) {
            log.info("用户登录失败, {}", user);
            return null;
        }
        // 脱敏
        User safetyUser = getSafetyUser(user);

        // 打包最终返回
        LoginVO result = new LoginVO();
        result.setUserInfo(user);
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_LOGIN_STATE, user);
        return result;
    }

    /**
     * 数据脱敏
     * @param user
     * @return
     */
    @Override
    public User getSafetyUser(User user) {
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setAccount(user.getAccount());
        safetyUser.setAvatar(user.getAvatar());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setRole(user.getRole());
        return  safetyUser;
    }
}




