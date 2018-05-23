package com.sandman.download.dao.mysql;

import com.sandman.download.entity.Authority;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunpeikai on 2018/5/22.
 */
@Repository
public interface AuthorityDao {
    public List<Authority> findByUserId(Long userId);
}
