package com.sandman.download.dao.mysql;

import com.sandman.download.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    public User findByUserName(String userName);
}
