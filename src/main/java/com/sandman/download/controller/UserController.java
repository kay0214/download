package com.sandman.download.controller;

import com.sandman.download.entity.common.BaseDto;
import com.sandman.download.entity.system.User;
import com.sandman.download.service.system.UserService;
import com.sandman.download.utils.ShiroSecurityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunpeikai on 2018/5/4.
 */
@RestController
@RequestMapping("/api/sandman/v1/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;
    /**
     * POST : Create a new user.
     */
    @PostMapping("/createUser")
    public BaseDto createUser(@RequestBody User user, String validateCode) throws URISyntaxException {//这里进行简单校验，在service里面进行复杂校验
        log.debug("REST request to save User : {},validateCode:{}", user,validateCode);
        if (user.getId() != null) {
            return new BaseDto(415,"创建用户你带什么ID啊!");
        }
        if(validateCode==null || "".equals(validateCode)){
            return new BaseDto(416,"请先填写验证码!");
        }

        return userService.createUser(user,validateCode);
    }
    @GetMapping("/getCurUserInfo")
    public BaseDto getCurUserInfo(){
        boolean isPermitted = ShiroSecurityUtils.isPermitted("getCurUserInfo");
        Subject currentSubject = ShiroSecurityUtils.getCurrentSubject();
        log.info("user[{}] has permission[getCurUserInfo]:::{}",ShiroSecurityUtils.getCurrentUserName(),isPermitted);
        log.info("has role[USER]:::{};has role[ADMIN]::::{}",currentSubject.hasRole("USER"),currentSubject.hasRole("ADMIN"));
        User user = userService.getCurUserInfo();
        if(user!=null){
            return new BaseDto(200,"查询成功!",user);
        }
        return new BaseDto(419,"用户未登录!");
    }
    @GetMapping("/contactExist")
    public Map<String, Integer> contactExist(String contact){
        Map<String,Integer> map = new HashMap<>();
        if(contact==null || "".equals(contact) || "null".equals(contact)){
            map.put("exist",0);
            return map;
        }
        map.put("exist",userService.contactExist(contact));//0:未传入联系方式；1:联系方式已经被绑定；2:联系方式未被绑定
        return map;
    }
    @PostMapping("/success")
    public BaseDto success(){
        log.info("login success!");
        return new BaseDto(200,"登录成功!");
    }
    @PostMapping("/error")
    public BaseDto error(){
        log.info("login error!");
        return new BaseDto(200,"登录失败!");
    }
    @PostMapping("/login")
    public BaseDto login(String username, String password, boolean rememberMe){
        log.info("login!!!!!username:{},password:{},remeberMe:{}",username,password,rememberMe);
        UsernamePasswordToken token = null;
        try{
            token = new UsernamePasswordToken(username, password);
            Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated()){
                log.info("准备进行登录验证!");
                token.setRememberMe(rememberMe);
                currentUser.login(token);
            }

        } catch (IncorrectCredentialsException e) {
            return new BaseDto(411,"登录密码错误");
        } catch (UnknownAccountException e) {
            return new BaseDto(423,"用户不存在");
        }
        return new BaseDto(200,"登录成功");
    }
}
