package com.sandman.download.service.system;

import com.sandman.download.dao.mysql.system.RoleDao;
import com.sandman.download.entity.system.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sunpeikai on 2018/5/23.
 */
@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;
    public List<Role> findByUserId(Long userId){
        return roleDao.findByUserId(userId);
    }
}
