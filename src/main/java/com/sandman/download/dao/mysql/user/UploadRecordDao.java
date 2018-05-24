package com.sandman.download.dao.mysql.user;

import com.sandman.download.entity.user.UploadRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sunpeikai on 2018/5/14.
 */
@Repository
public interface UploadRecordDao {
    public List<UploadRecord> findAllByUserId(Long id);
    public Long createUploadRecord(UploadRecord uploadRecord);
    public void deleteById(UploadRecord uploadRecord);//假删
}
