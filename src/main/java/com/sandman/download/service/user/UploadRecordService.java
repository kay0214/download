package com.sandman.download.service.user;

import com.github.pagehelper.PageHelper;
import com.sandman.download.dao.mysql.user.UploadRecordDao;
import com.sandman.download.entity.user.UploadRecord;
import com.sandman.download.utils.PageBean;
import com.sandman.download.utils.ShiroSecurityUtils;
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
    public Long createUploadRecord(UploadRecord uploadRecord) {
        log.debug("Request to save UploadRecord : {}", uploadRecord);
        uploadRecordDao.createUploadRecord(uploadRecord);
        return uploadRecord.getId();
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
     * 根据userId获取记录
     */
    @Transactional(readOnly = true)
    public Map getAllUploadRecords(Integer pageNumber, Integer size){

        Long userId = ShiroSecurityUtils.getCurrentUserId();
        log.info("userId:{}",userId);
        if(userId==null)
            return null;
        pageNumber = (pageNumber==null || pageNumber<1)?1:pageNumber;
        size = (size==null || size<0)?10:size;

        String orderBy = "createTime desc";//默认按照createTime降序排序

        Integer totalRow = uploadRecordDao.findAllByUserId(userId).size();//查询出数据条数

        PageHelper.startPage(pageNumber,size).setOrderBy(orderBy);

        List<UploadRecord> resources = uploadRecordDao.findAllByUserId(userId);//查询出列表（已经分页）
        PageBean<UploadRecord> pageBean = new PageBean<>(pageNumber,size,totalRow);//这里是为了计算页数，页码

        pageBean.setItems(resources);
        List<UploadRecord> result = pageBean.getItems();

/*        result.forEach(uploadRecord -> {
            log.info(uploadRecord.toString());
        });*/
        Map data = new HashMap();//最终返回的map

        data.put("totalRow",totalRow);
        data.put("totalPage",pageBean.getTotalPage());
        data.put("currentPage",pageBean.getCurrentPage());//默认0就是第一页
        data.put("uploadRecordList",result);
        return data;

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
