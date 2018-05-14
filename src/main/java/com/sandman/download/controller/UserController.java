package com.sandman.download.controller;

import com.sandman.download.entity.BaseDto;
import com.sandman.download.entity.User;
import com.sandman.download.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangj on 2018/5/4.
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
    public BaseDto createUser(@RequestBody User user,String validateCode) throws URISyntaxException {//这里进行简单校验，在service里面进行复杂校验
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
    public void success(){
        log.info("login success!");
    }
    @PostMapping("/login")
    public void login(){
        log.info("login!");
    }
}
