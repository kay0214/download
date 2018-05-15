package com.sandman.download.dao.mysql;

import com.sandman.download.entity.DownloadRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangj on 2018/5/14.
 */
@Repository
public interface DownloadRecordDao {
    public List<DownloadRecord> findAllByUserId(Long userId);
    public Long createDownloadRecord(DownloadRecord downloadRecord);
    public void deleteById(DownloadRecord downloadRecord);//假删
}
