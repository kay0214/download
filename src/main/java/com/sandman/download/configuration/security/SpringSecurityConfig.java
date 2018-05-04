package com.sandman.download.configuration.security;

import com.sandman.download.security.UserAndAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SessionRegistry sessionRegistry;
    @Autowired
    private UserAndAuthService userAndAuthService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/sandman/v1/user/createUser").permitAll()//用户注册->开放接口
                .antMatchers(" /api/sandman/v1/uploadRecord/getAllRecords").authenticated()//用户查看上传记录，只能查看自己的
                .antMatchers(" /api/sandman/v1/downloadRecord/getAllDownloadRecords").authenticated()//用户查看下载记录，只能查看自己的
                .antMatchers(" /api/sandman/v1/resourceRecord/getAllResourceRecord").authenticated()//用户查看积分明细，只能查看自己的
                .antMatchers(" /api/sandman/v1/resource/updateResource").authenticated()//用户更新自己的资源信息，所以要登录
                .antMatchers("/api/sandman/v1/resource/uploadResource").authenticated()//用户上传资源，需要登录
                .antMatchers("/api/sandman/v1/resource/downloadResource").authenticated()//用户下载资源，需要登录
                .antMatchers(" /api/sandman/v1/resource/getAllMyResources").permitAll()//查看资源列表，开放接口无需登录
                .antMatchers("/api/sandman/v1/resource/delResource").authenticated()//资源假删，需要登录，自己上传的资源才有权删除
                .antMatchers("/api/sandman/v1/resource/getManyResourcesByFuzzy").permitAll()//资源检索，输入框查询，无需登录
                .antMatchers("/api/sandman/v1/resource/getOneResource").permitAll()//查看资源详情，无需登录
                .antMatchers("/management/health").permitAll()
                //.antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .antMatchers("/swagger-resources/configuration/ui").permitAll()

                .and()
                .formLogin()
                .loginProcessingUrl("/api/sandman/v1/user/login")//登录接口
                .successForwardUrl("/api/sandman/v1/user/success")
                //.successHandler()
                //.defaultSuccessUrl("/api/sandman/v1/user/success",true)
                .failureForwardUrl("/api/sandman/v1/user/error")//登录失败页面
                //.defaultSuccessUrl("/api/sandman/v1/user/login")//登录成功页面
                .and()
                .logout()
                .logoutUrl("/api/sandman/v1/user/logout").permitAll()//登出接口
                .logoutSuccessUrl("/api/sandman/v1/user/logoutSuccess")//登出成功页面
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .rememberMe()
                .rememberMeParameter("rememberMe")
                .tokenValiditySeconds(60 * 60 * 24 * 7)//一周时间，记住我功能
                .rememberMeCookieName("rememberMe")
                //.and()
                //.anyRequest().authenticated()
                .and()
                .sessionManagement()
                .maximumSessions(1)//最多一处登录
                .sessionRegistry(sessionRegistry)
                .and()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAndAuthService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SessionRegistry getSessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }
}
