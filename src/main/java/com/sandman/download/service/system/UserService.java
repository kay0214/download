package com.sandman.download.service.system;

import com.sandman.download.dao.mysql.system.UserDao;
import com.sandman.download.entity.common.BaseDto;
import com.sandman.download.entity.system.Permission;
import com.sandman.download.entity.system.Role;
import com.sandman.download.entity.system.User;
import com.sandman.download.entity.user.ValidateCode;
import com.sandman.download.service.user.ValidateCodeService;
import com.sandman.download.utils.PasswordEncrypt;
import com.sandman.download.utils.RandomUtils;
import com.sandman.download.utils.ShiroSecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunpeikai on 2018/5/4.
 */
@Service
public class UserService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserDao userDao;
    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    /**
     * 注册用户
     */
    public BaseDto createUser(User user,String validateCode) {
        log.info("Request to save User : {}，validateCode:{}", user,validateCode);
        User existUser = userDao.findByUserName(user.getUserName());
        if(existUser!=null)//用户名校验
            return new BaseDto(409,"用户名已存在!");

        //验证码过期校验
        boolean overdue = overdueCode(user);
        if(overdue){//已经过期return，未过期就继续往下走
            deleteValidateCode(user);//验证码过期，异步删除验证码
            return new BaseDto(418,"未发送验证码或验证码已过期!");
        }

        //验证码正确性校验
        boolean verifySuccess = verifyCode(user,validateCode);
        if(!verifySuccess)//校验失败return，否则继续往下走
            return new BaseDto(417,"验证码不正确!",user);
        //所有校验已经完成，创建用户
        user.setSalt(user.getUserName() + RandomUtils.getUuidStr());//用户盐 = userName + 随机uuid
        user.setPassword(PasswordEncrypt.getEncryptedPwdBySalt(user.getPassword(),user.getSalt()));//密码加密 use salt
        user.setGold(0);
        user.setAvailable(1);
        user.setCreateBy(1L);//1:系统注册
        user.setCreateTime(ZonedDateTime.now());
        user.setUpdateBy(1L);
        user.setUpdateTime(ZonedDateTime.now());
        user.setDelFlag(0);

        //User user = userMapper.toEntity(userDTO);
        //user = userRepository.save(user);
        userDao.createUser(user);
        deleteValidateCode(user);//注册完成，异步删除验证码
        return new BaseDto(200,"注册成功!",user);

    }
    public User getCurUserInfo(){
        String userName = ShiroSecurityUtils.getCurrentUserName();
        log.info("getCurUserInfo:::::" + userName);
        if(userName==null || "".equals(userName))
            return null;
        User currentUser = userDao.findByUserName(userName);
        currentUser.setPassword(null);
        currentUser.setMobile(null);
        currentUser.setEmail(null);
        return currentUser;
    }
    public User updateUser(User user){
        user.setUpdateTime(ZonedDateTime.now());
        log.info("update User:{}",user.toString());
        userDao.updateUser(user);
        return user;
    }
    /**
     * 验证联系方式是否已经被绑定，这里判定是何种联系方式的方法是正则表达式
     * */
    public BaseDto contactExist(String contact){
        List<User> userList = new ArrayList<>();
        if(contact.contains("@")){//验证邮箱是否已经被绑定
            userList = userDao.findByEmail(contact);
        }else{//验证手机号是否已经被绑定
            userList = userDao.findByMobile(contact);
        }
        Map map = new HashMap();
        if(userList.size()>0) {//userList>0，表示已经存在，返回1
            map.put("exist",1);
            return new BaseDto(426,"联系方式已经被绑定",map);
        }
        map.put("exist",2);
        return new BaseDto(200,"联系方式未被绑定",map);
    }
    /**
     * 验证码过期校验
     * */
    public boolean overdueCode(User user){//直接按照邮箱找，省了发手机短信的钱。后期要改造这里
        ValidateCode validateCode = validateCodeService.findByContact(user.getEmail());
        if(null==validateCode || validateCode.getDeadLine().isBefore(ZonedDateTime.now())){//截止时间在当前时间之前，即过期
            return true;
        }
        return false;
    }
    /**
     * 验证码正确性校验
     * */
    public boolean verifyCode(User user,String code){//直接按照邮箱找，省了发手机短信的钱。后期要改造这里
        ValidateCode validateCode = validateCodeService.findByContact(user.getEmail());
        if(null==validateCode || validateCode.getDeadLine().isBefore(ZonedDateTime.now())){//截止时间在当前时间之前，即过期
            return false;
        }
        return code.equals(validateCode.getCode());
    }
    /**
     * 删除已经完成注册的验证码和校验失败的验证码.异步删除
     * */
    @Async
    public void deleteValidateCode(User user){
        validateCodeService.deleteByContact(user.getEmail());//如果是使用email注册成功，按照email删除验证码
        validateCodeService.deleteByContact(user.getMobile());//如果是使用手机号注册成功，按照手机号删除验证码
    }

    /**
     * Get one user by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public User findOne(Long id) {
        log.debug("Request to get User : {}", id);
        return userDao.findById(id);
    }

    /**
     * Delete the user by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete User : {}", id);
        User user = new User();
        user.setId(id);
        user.setUpdateBy(1L);
        user.setUpdateTime(ZonedDateTime.now());
        user.setDelFlag(1);
        userDao.updateUser(user);
    }
    /**
     * 根据userName获取User信息
     * */
    public User findUserByUserName(String userName){
        User user = userDao.findByUserName(userName);
        if(user!=null){//根据userName查询出来的用户不为空
            Long userId = user.getId();
            List<Role> roleList = roleService.findByUserId(userId);
            for(Role role:roleList){
                List<Permission> permissionList = permissionService.findByRoleId(role.getId());
                role.setPermissionList(permissionList);
            }
            user.setRoleList(roleList);
            return user;
        }
        return null;
    }
}
