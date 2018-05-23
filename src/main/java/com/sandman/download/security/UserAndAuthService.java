package com.sandman.download.security;

import com.sandman.download.dao.mysql.AuthorityDao;
import com.sandman.download.dao.mysql.UserDao;
import com.sandman.download.entity.Authority;
import com.sandman.download.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserAndAuthService implements UserDetailsService {

/*    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        System.out.println("userName=" + userName);
        User user = userDao.findByUserName(userName);

        if (user != null) {
            List<Authority> authoritys = authorityDao.findByUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Authority authority : authoritys) {
                if (authority != null && authority.getAuthName()!=null) {
                    GrantedAuthority grantedAuthority = new MyGrantedAuthority(authority.getAuthorityUrl(),authority.getMethod());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            user.setAuthorities(grantedAuthorities);
            user.setUserName(user.getUserName());
            user.setNickName(user.getNickName());
            user.setId(user.getId());
            user.setMobile(user.getMobile());
            user.setEmail(user.getEmail());
            user.setGold(user.getGold());
            return user;
        } else {
            throw new UsernameNotFoundException("admin: " + userName + " do not exist!");
        }
    }*/


    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //if(SecurityUtils.getCurrentUser()!=null)
            //return SecurityUtils.getCurrentUser();
        System.out.println("loadUserByUsername:::::userName=" + userName);
        User user = userDao.findByUserName(userName);
        if (user == null)
            throw new UsernameNotFoundException("user not found!");
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        user.setAuthorities(authorityList);
        System.out.println(user.toString());
        return user;
/*        CurrentUser userAndAuth = new CurrentUser(user.getUserName(), user.getPassword(), authorityList);
        userAndAuth.setUserName(user.getUserName());
        userAndAuth.setNickName(user.getNickName());
        userAndAuth.setId(user.getId());
        userAndAuth.setMobile(user.getMobile());
        userAndAuth.setEmail(user.getEmail());
        userAndAuth.setGold(user.getGold());
        System.out.println("userAndAuth========" + userAndAuth.toString());
        return userAndAuth;*/
    }
}
