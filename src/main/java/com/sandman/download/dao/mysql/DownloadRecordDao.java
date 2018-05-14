package com.sandman.download.dao.mysql;

import com.sandman.download.entity.DownloadRecord;
import org.springframework.stereotype.Repository;

/**
 * Created by wangj on 2018/5/14.
 */
@Repository
public interface DownloadRecordDao {
    public Long createDownloadRecord(DownloadRecord downloadRecord);
    public void deleteById(DownloadRecord downloadRecord);//假删
}
