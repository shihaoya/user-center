package com.hao.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.hao.usercenter.common.ErrorCode;
import com.hao.usercenter.constant.UserConstant;
import com.hao.usercenter.exception.BusinessException;
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
    public Long register(RegisterRequest registerRequest) {
        String account = registerRequest.getAccount();
        String password = registerRequest.getPassword();
        String confirmPassword = registerRequest.getConfirmPassword();
        String planetCode = registerRequest.getPlanetCode();
        // 字段不能为空
        if (StringUtils.isAllBlank(account, password, confirmPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        // 账号4-16位
        if (account.length() < 4 || account.length() > 16) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度4~16位");
        }
        // 密码8-16位
        if (password.length() < 8 || password.length() > 16 || confirmPassword.length() < 8 || confirmPassword.length() > 16) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度8~16位");
        }
        // 密码不一致
        if (!password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致");
        }
        // 不要特殊字符
        String validPattern = "^[a-zA-Z0-9]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(account);
        if (!matcher.matches()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不要使用特殊字符");
        }
        // 账号不能重复
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("account", account);
        long count = userMapper.selectCount(qw);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 星球编号不能重复
        qw = new QueryWrapper<>();
        qw.eq("planet_code", planetCode);
        count = userMapper.selectCount(qw);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号重复");
        }

        // 加密
        String encryptPasswd = DigestUtils.md5DigestAsHex((UserConstant.SALT + password).getBytes());

        // 插入数据
        User user = new User();
        user.setAccount(account);
        user.setPassword(encryptPasswd);
        user.setPlanetCode(planetCode);
        log.info("注册用户：{}", user);
        return (long) userMapper.insert(user);
    }

    @Override
    public LoginVO login(LoginReqeust loginReqeust, HttpServletRequest request) {
        String account = loginReqeust.getAccount();
        String password = loginReqeust.getPassword();

        // 字段不能为空
        if (StringUtils.isAllBlank(account, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数不能为空");
        }
        // 账号4-16位
        if (account.length() < 4 || account.length() > 16) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度4~16位");
        }
        // 密码8-16位
        if (password.length() < 8 || password.length() > 16) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度8~16位");
        }
        // 不要特殊字符
        String validPattern = "^[a-zA-Z0-9]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(account);
        if (!matcher.matches()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不要使用特殊字符");
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
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        // 脱敏
        User safetyUser = getSafetyUser(user);

        // 打包最终返回
        LoginVO result = new LoginVO();
        result.setUserInfo(safetyUser);
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_LOGIN_STATE, safetyUser);
        log.info("登入用户：{}", safetyUser);
        return result;
    }

    @Override
    public Boolean logout(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        log.info("登出用户：{}", user);
        return true;
    }

    /**
     * 数据脱敏
     * @param user
     * @return
     */
    @Override
    public User getSafetyUser(User user) {
        if (user == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setAccount(user.getAccount());
        safetyUser.setAvatar(user.getAvatar());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setRole(user.getRole());
        safetyUser.setPlanetCode(user.getPlanetCode());
        return  safetyUser;
    }
}




