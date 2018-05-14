package com.sandman.download.service;

import com.sandman.download.dao.mysql.UploadRecordDao;
import com.sandman.download.entity.UploadRecord;
import com.sandman.download.security.SecurityUtils;
import com.sandman.download.utils.FileUtils;
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
 * Service Implementation for managing UploadRecord.
 */
@Service
@Transactional
public class UploadRecordService {

    private final Logger log = LoggerFactory.getLogger(UploadRecordService.class);

    @Autowired
    private UploadRecordDao uploadRecordDao;
    /**
     * 创建一个上传记录
     */
    public UploadRecord createUploadRecord(UploadRecord uploadRecord) {
        log.debug("Request to save UploadRecord : {}", uploadRecord);
        Long id = uploadRecordDao.createUploadRecord(uploadRecord);
        log.info("id设置之前:{}",id);
        uploadRecord.setId(id);
        return uploadRecord;
    }
    /**
     * 根据id删除数据
     * */
    public void deleteById(Long id){
        log.info("delete data by id :{}",id);
        UploadRecord uploadRecord = new UploadRecord();
        uploadRecord.setId(id);
        uploadRecord.setDelFlag(1);
        uploadRecord.setUpdateTime(ZonedDateTime.now());
        uploadRecordDao.deleteById(uploadRecord);
    }

    /**
     * Get all the uploadRecords page.
     */
    @Transactional(readOnly = true)
    public Map getAllUploadRecords(Integer pageNumber, Integer size)throws Exception {
        return null;
/*        log.debug("Request to get all UploadRecords");
        pageNumber = (pageNumber==null || pageNumber<1)?1:pageNumber;
        size = (size==null || size<0)?10:size;
        Pageable pageable = PageableTools.basicPage(pageNumber,size,new SortDto("desc","recordTime"));
        Page<UploadRecord> uploadRecordPage = uploadRecordRepository.findAllByUserId(SecurityUtils.getCurrentUserId(),pageable);
        Map data = new HashMap();
        data.put("totalRow",uploadRecordPage.getTotalElements());
        data.put("totalPage",uploadRecordPage.getTotalPages());
        data.put("currentPage",uploadRecordPage.getNumber()+1);//默认0就是第一页
        data.put("resourceList",getFileSizeHaveUnit(uploadRecordPage.getContent()));

        return data;*/
    }
    /**
     * 资源大小：存入数据库的时候统一以byte为单位，取出来给前端的时候要做规范 -> 转换成以 B,KB,MB,GB为单位
     * */
    public List getFileSizeHaveUnit(List<UploadRecord> resourceList){
/*        resourceList.forEach(uploadRecord -> {//这里必须捕获异常，否则如果size一样的话，会抛出异常
            try{
                String size = uploadRecord.getRes().getResSize();
                uploadRecord.getRes().setResSize(FileUtils.getFileSize(Long.parseLong(size)));
            }catch(Exception e){
                log.info("size相同，抛出Numberformat异常!");
                String size = uploadRecord.getRes().getResSize();
                uploadRecord.getRes().setResSize(size);
            }
        });

        return resourceList;*/
return null;
    }
}
