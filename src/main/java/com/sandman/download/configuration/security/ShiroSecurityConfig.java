package com.sandman.download.configuration.security;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroSecurityConfig {

    /**
     * cookie rememberMe
     * */
    @Bean(name = "rememberMeCookie")
    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie();
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24*7);//7天记住我
        cookie.setName("rememberMe");
        return cookie;
    }
    /**
     * cookie rememberMe manager
     * */
    public CookieRememberMeManager cookieRememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setEncryptionCipherKey("COOKIEKEYFORSANDMAN".getBytes());
        cookieRememberMeManager.setDecryptionCipherKey("COOKIEKEYFORSANDMAN".getBytes());
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));//这里的密码真的不知道该怎么生成
        return cookieRememberMeManager;
    }
    /**
     * securityManager
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        securityManager.setRememberMeManager(cookieRememberMeManager());//cookie
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        securityManager.setSubjectFactory(new DefaultWebSubjectFactory());
        return securityManager;
    }
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(1024);//散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashSalted(true);
        return hashedCredentialsMatcher;
    }
    /**
     * Realm 用于获取用户认证信息以及权限信息
     * shiro有几个默认的，默认Realm如果不能满足要求需要自定义
     * @return
     */
    @Bean(name = "myRealm")
    public Realm myRealm(){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myRealm;
    }

    /**
     * Shiro Config
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //Login
        String loginUrl = "";
        shiroFilterFactoryBean.setLoginUrl(loginUrl);

        //Shiro内的filter配置
        Map<String, Filter> filters = new HashMap<>();

        //定义logoutFilter并添加
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("");
        filters.put("logout",logoutFilter);
        //添加到shiro chain里
        shiroFilterFactoryBean.setFilters(filters);

        //配置拦截规则
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout.do","logout");
        filterChainDefinitionMap.put("/casReturn.do","authc");
        filterChainDefinitionMap.put("/**/*.js","anon");
        filterChainDefinitionMap.put("/assets/**","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/images/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/api/sandman/v1/user/login","anon");
        filterChainDefinitionMap.put("/api/sandman/v1/uploadRecord/getAllRecords","authc");
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
}
