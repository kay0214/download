package com.sandman.download.dao.mysql.user;

import com.sandman.download.entity.user.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunpeikai on 2018/5/14.
 */
@Repository
public interface ResourceDao {
    public void createResource(Resource resource);
    public void updateById(Resource resource);//更新数据
    public List<Resource> findByUserId(Long userId);//根据用户名查询资源list
    public Integer getCountByUserId(Long userId);
    public List<Resource> findManyResourcesByFuzzy(String searchContent);//模糊检索
    public Resource findById(Long id);//根据资源id查询具体的资源
}
