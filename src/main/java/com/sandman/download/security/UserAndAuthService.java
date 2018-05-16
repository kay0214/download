package com.sandman.download.security;

import com.sandman.download.dao.mysql.UserDao;
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
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if(SecurityUtils.getCurrentUser()!=null)
            return SecurityUtils.getCurrentUser();
        System.out.println("userName=" + userName);
        User user = userDao.findByUserName(userName);
        System.out.println(user.toString());
        if (user == null)
            throw new UsernameNotFoundException("user not found!");
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        CurrentUser userAndAuth = new CurrentUser(user.getUserName(), user.getPassword(), authorityList);
        userAndAuth.setUserName(user.getUserName());
        userAndAuth.setNickName(user.getNickName());
        userAndAuth.setId(user.getId());
        userAndAuth.setMobile(user.getMobile());
        userAndAuth.setEmail(user.getEmail());
        userAndAuth.setGold(user.getGold());
        return userAndAuth;
    }
}
