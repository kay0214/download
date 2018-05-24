package com.sandman.download.configuration.security;

import com.sandman.download.dao.mysql.system.UserDao;
import com.sandman.download.entity.system.User;
import com.sandman.download.utils.PasswordEncrypt;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by renhuiyu on 2018/5/23.
 */
public class MyRealm extends AuthorizingRealm{
	@Autowired
	UserDao userDao;
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		String userName = usernamePasswordToken.getUsername();

		User user = userDao.findByUserName(userName);
		String salt = user.getSalt();
		String password = PasswordEncrypt.getEncryptedPwdBySalt(String.valueOf(usernamePasswordToken.getPassword()),salt);
		if(user != null){
			// 这里我设置的principal传的是user实体
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
					password,
					ByteSource.Util.bytes(salt),
					this.getName());
			return info;
		}else{
			System.out.println("用户名不存在!");
			throw new UnknownAccountException("用户不存在");
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
		String userName = (String) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();

		return info;
	}
}
