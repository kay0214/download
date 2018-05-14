package com.sandman.download.service;


import com.sandman.download.dao.mysql.DownloadRecordDao;
import com.sandman.download.entity.DownloadRecord;
import com.sandman.download.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing DownloadRecord.
 */
@Service
@Transactional
public class DownloadRecordService {

    private final Logger log = LoggerFactory.getLogger(DownloadRecordService.class);

    @Autowired
    private DownloadRecordDao downloadRecordDao;
    /**
     * 创建一个下载记录
     */
    public DownloadRecord createDownloadRecord(Long resId) {
        Long userId = SecurityUtils.getCurrentUserId();
        DownloadRecord downloadRecord = new DownloadRecord();
        downloadRecord.setUserId(userId);
        downloadRecord.setResId(resId);

        downloadRecord.setCreateBy(userId);
        downloadRecord.setCreateTime(ZonedDateTime.now());
        downloadRecord.setUpdateBy(userId);
        downloadRecord.setUpdateTime(ZonedDateTime.now());
        downloadRecord.setDelFlag(0);
        Long id = downloadRecordDao.createDownloadRecord(downloadRecord);
        log.info("create download record return id:{}",id);
        downloadRecord.setId(id);
        return downloadRecord;
    }

    /**
     * 获取用户的下载记录（分页）
     */
/*    @Transactional(readOnly = true)
    public Map getAllDownloadRecords(Integer pageNumber, Integer size)throws Exception {
        log.debug("Request to get all DownloadRecords");
        pageNumber = (pageNumber==null || pageNumber<1)?1:pageNumber;
        size = (size==null || size<0)?10:size;

        List<DownloadRecord> downloadRecordList = downloadRecordDao.findAllByUserId(SecurityUtils.getCurrentUserId(),pageNumber,size);


        downloadRecordList.forEach(downloadRecord -> {
            System.out.println(downloadRecord.getRes().getResSize());
        });
        Map data = new HashMap();
        data.put("totalPage",downloadRecordPage.getTotalPages());
        data.put("currentPage",downloadRecordPage.getNumber()+1);//默认0就是第一页
        data.put("resourceList",downloadRecordList);

        return data;
    }*/
    /**
     * 资源大小：存入数据库的时候统一以byte为单位，取出来给前端的时候要做规范 -> 转换成以 B,KB,MB,GB为单位
     * */
/*    public List getFileSizeHaveUnit(List<DownloadRecord> resourceList){
        resourceList.forEach(resource -> {//这里必须捕获异常，否则如果size一样的话，会抛出异常
            try{
                String size = resource.getRes().getResSize();
                resource.getRes().setResSize(FileUtils.getFileSize(Long.parseLong(size)));
            }catch(Exception e){
                String size = resource.getRes().getResSize();
                resource.getRes().setResSize(size);
            }

        });
        return resourceList;
    }*/
    /**
     * Delete the downloadRecord by id.
     */
    public void deleteById(Long id) {
        log.debug("Request to delete download record : {}", id);
        DownloadRecord downloadRecord = new DownloadRecord();
        downloadRecord.setId(id);
        downloadRecord.setUpdateBy(SecurityUtils.getCurrentUserId());
        downloadRecord.setUpdateTime(ZonedDateTime.now());
        downloadRecord.setDelFlag(1);
        downloadRecordDao.deleteById(downloadRecord);
    }
}
