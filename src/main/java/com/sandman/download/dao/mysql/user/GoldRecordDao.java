package com.sandman.download.dao.mysql.user;

import com.sandman.download.entity.user.GoldRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunpeikai on 2018/5/14.
 */
@Repository
public interface GoldRecordDao {
    public List<GoldRecord> findAllByUserId(Long userId);
    public Long createGoldRecord(GoldRecord goldRecord);
    public void deleteById(GoldRecord goldRecord);//假删
}
