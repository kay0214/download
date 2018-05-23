package com.sandman.download.configuration.security;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringSecurityConfig{
    /**
     * securityManager
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        securityManager.setCacheManager(new MemoryConstrainedCacheManager());
        securityManager.setSubjectFactory(new DefaultWebSubjectFactory());
        return securityManager;
    }

    /**
     * Realm 用于获取用户认证信息以及权限信息
     * shiro有几个默认的，默认Realm如果不能满足要求需要自定义
     * @return
     */
    @Bean(name = "myRealm")
    public Realm myRealm(){
        return new MyRealm();
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

        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
}
