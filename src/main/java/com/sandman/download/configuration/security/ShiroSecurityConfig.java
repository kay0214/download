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
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
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
     * CachingShiroSessionDao
     */
    @Bean(name = "shiroSessionDao")
    public ShiroSessionDao shiroSessionDao(){
        return new ShiroSessionDao();
    }
    /**
     * securityManager
     * @return
     */
    /**
     * EhCacheManager,缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来
     */
    @Bean(name = "sessionFactory")
    public ShiroSessionFactory shiroSessionFactory() {
        return new ShiroSessionFactory();
    }

    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        //manager.setCacheManager(cacheManager);// 加入缓存管理器
        manager.setSessionFactory(shiroSessionFactory());//设置sessionFactory
        manager.setSessionDAO(shiroSessionDao());// 设置SessionDao
        manager.setDeleteInvalidSessions(true);// 删除过期的session
        manager.setGlobalSessionTimeout(shiroSessionDao().getExpireTime());// 设置全局session超时时间
        manager.setSessionValidationSchedulerEnabled(true);// 是否定时检查session
        return manager;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        securityManager.setRememberMeManager(cookieRememberMeManager());//cookie
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        securityManager.setSubjectFactory(new DefaultWebSubjectFactory());
        securityManager.setSessionManager(sessionManager());
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
        //String loginUrl = "http://www.baidu.com";
        //String loginUrl = "http://39.104.80.30/login";
        String loginUrl = "";
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        //shiroFilterFactoryBean.setSuccessUrl("/api/sandman/v1/user/success");
        //shiroFilterFactoryBean.setUnauthorizedUrl("http://39.104.80.30/login");
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
        filterChainDefinitionMap.put("/api/sandman/v1/user/login","anon");//登录接口，匿名用户可访问
        filterChainDefinitionMap.put("/api/sandman/v1/user/logout","authc");//退出接口，登录用户可访问
        filterChainDefinitionMap.put("/api/sandman/v1/resource/getManyResourcesByFuzzy","anon");//首页检索资源接口，匿名用户可访问
        filterChainDefinitionMap.put("/api/sandman/v1/resource/getAllMyResources","anon");//搜索我的资源，匿名用户可访问
        filterChainDefinitionMap.put("/api/sandman/v1/validateCode/sendValidateCode","anon");//注册用户时发送邮件验证码接口，匿名用户可访问
        filterChainDefinitionMap.put("/api/sandman/v1/user/contactExist","anon");//验证联系方式是否已经被绑定接口，匿名用户可访问
        filterChainDefinitionMap.put("/api/sandman/v1/user/createUser","anon");//创建用户接口，匿名用户可访问
        filterChainDefinitionMap.put("/api/sandman/v1/uploadRecord/getAllRecords","authc");
        filterChainDefinitionMap.put("/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}
