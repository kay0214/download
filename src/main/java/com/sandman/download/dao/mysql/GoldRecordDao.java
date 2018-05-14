package com.sandman.download.dao.mysql;

import com.sandman.download.entity.GoldRecord;
import org.springframework.stereotype.Repository;

/**
 * Created by wangj on 2018/5/14.
 */
@Repository
public interface GoldRecordDao {
    public Long createGoldRecord(GoldRecord goldRecord);
    public void deleteById(GoldRecord goldRecord);//假删
}
