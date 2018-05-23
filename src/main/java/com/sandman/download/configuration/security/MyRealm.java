package com.sandman.download.configuration.security;

import com.sandman.download.dao.mysql.UserDao;
import com.sandman.download.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by renhuiyu on 2018/5/23.
 */
public class MyRealm extends AuthorizingRealm
{
	@Autowired UserDao userDao;
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
		throws AuthenticationException
	{
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		String userName = usernamePasswordToken.getUsername();
		String password = String.valueOf(usernamePasswordToken.getPassword());
		User user = userDao.findByUserName(userName);
		if(user == null){
			return null;
		}
		if(!user.getPassword().equals(password)){
			return null;
		}
		// 这里我设置的principal传的是user实体
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,password,this.getName());
		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		String userName = (String) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();

		return info;
	}
}
