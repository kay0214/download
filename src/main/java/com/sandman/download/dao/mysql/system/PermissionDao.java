package com.sandman.download.dao.mysql.system;

import com.sandman.download.entity.system.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunpeikai on 2018/5/22.
 */
@Repository
public interface PermissionDao {
    public List<Permission> findByRoleId(Long roleId);
}
