package com.sandman.download.utils;

import com.sandman.download.entity.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by sunpeikai on 2018/5/24.
 */
public class ShiroSecurityUtils {
    public static User getCurrentUser(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user;
    }
    /**
     * 返回当前用户的id
     * */
    public static Long getCurrentUserId(){
        try{
            return getCurrentUser().getId();
        }catch(NullPointerException e){
            return null;
        }
    }
    /**
     * 返回当前用户的userName
     * */
    public static String getCurrentUserName(){
        try{
            return getCurrentUser().getUserName();
        }catch(NullPointerException e){
            return null;
        }
    }
    /**
     * 返回当前用户的nickName
     * */
    public static String getCurrentNickName(){
        try{
            return getCurrentUser().getNickName();
        }catch(NullPointerException e){
            return null;
        }
    }
    /**
     * 返回当前用户的mobile
     * */
    public static String getCurrentUserMobile(){
        try{
            return getCurrentUser().getMobile();
        }catch(NullPointerException e){
            return null;
        }
    }
    /**
     * 返回当前用户的email
     * */
    public static String getCurrentUserEmail(){
        try{
            return getCurrentUser().getEmail();
        }catch(NullPointerException e){
            return null;
        }
    }
    /**
     * 返回当前用户的gold积分
     * */
    public static Integer getCurrentUserGold(){
        try{
            return getCurrentUser().getGold();
        }catch(NullPointerException e){
            return null;
        }
    }
}
