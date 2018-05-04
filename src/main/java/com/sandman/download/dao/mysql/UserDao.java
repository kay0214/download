package com.sandman.download.dao.mysql;

import com.sandman.download.entity.User;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserDao {
    public User findByUserName(String userName);
    public void createUser(User user);
    public void updateUser(User user);
    public List<User> findByEmail(String email);
    public List<User> findByMobile(String mobile);
    public User findById(Long id);
}
