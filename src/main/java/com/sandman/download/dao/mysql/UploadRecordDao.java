package com.sandman.download.dao.mysql;

import com.sandman.download.entity.UploadRecord;
import org.springframework.stereotype.Repository;

/**
 * Created by wangj on 2018/5/14.
 */
@Repository
public interface UploadRecordDao {
    public Long createUploadRecord(UploadRecord uploadRecord);
    public void deleteById(UploadRecord uploadRecord);//假删
}
