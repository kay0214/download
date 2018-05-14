package com.sandman.download.service;

import com.sandman.download.dao.mysql.GoldRecordDao;
import com.sandman.download.entity.GoldRecord;
import com.sandman.download.entity.Resource;
import com.sandman.download.entity.User;
import com.sandman.download.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Service Implementation for managing ResourceRecord.
 */
@Service
@Transactional
public class GoldRecordService {

    private final Logger log = LoggerFactory.getLogger(GoldRecordService.class);

    @Autowired
    private GoldRecordDao goldRecordDao;
    /**
     * Save a resourceRecord.
     *
     * @param resourceRecordDTO the entity to save
     * @return the persisted entity
     */
/*    public ResourceRecordDTO save(ResourceRecordDTO resourceRecordDTO) {
        log.debug("Request to save ResourceRecord : {}", resourceRecordDTO);
        ResourceRecord resourceRecord = resourceRecordMapper.toEntity(resourceRecordDTO);
        resourceRecord = resourceRecordRepository.save(resourceRecord);
        return resourceRecordMapper.toDto(resourceRecord);
    }*/

    /**
     * Get all the resourceRecords.
     *
     * @return the list of entities
     */
/*    @Transactional(readOnly = true)
    public Map getAllResourceRecords(Integer pageNumber, Integer size)throws Exception {
        log.debug("Request to get all ResourceRecords");
        pageNumber = (pageNumber==null || pageNumber<1)?1:pageNumber;
        size = (size==null || size<0)?10:size;
        Pageable pageable = PageableTools.basicPage(pageNumber,size,new SortDto("desc","recordTime"));
        Page resourceRecordPage = resourceRecordRepository.findAllByUserId(SecurityUtils.getCurrentUserId(),pageable);
        Map data = new HashMap();
        data.put("totalRow",resourceRecordPage.getTotalElements());
        data.put("totalPage",resourceRecordPage.getTotalPages());
        data.put("currentPage",resourceRecordPage.getNumber()+1);//默认0就是第一页
        data.put("recordList",resourceRecordPage.getContent());
        return data;
    }*/

    /**
     * Get one resourceRecord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
/*    @Transactional(readOnly = true)
    public ResourceRecordDTO findOne(Long id) {
        log.debug("Request to get ResourceRecord : {}", id);
        ResourceRecord resourceRecord = resourceRecordRepository.findOne(id);
        return resourceRecordMapper.toDto(resourceRecord);
    }*/

    /**
     * Delete the resourceRecord by id.
     *
     * @param id the id of the entity
     */
    public void deleteById(Long id) {
        log.debug("Request to delete ResourceRecord : {}", id);
        GoldRecord goldRecord = new GoldRecord();
        goldRecord.setId(id);
        goldRecord.setUpdateBy(SecurityUtils.getCurrentUserId());//用户操作
        goldRecord.setUpdateTime(ZonedDateTime.now());
        goldRecord.setDelFlag(1);
        goldRecordDao.deleteById(goldRecord);//假删
    }

    /**
     * 积分增加
     * */
    public GoldRecord addGold(User user, Resource resource,int gold,String opDesc){//resourceDTO和gold 二者其一不为null即可
        GoldRecord goldRecord = new GoldRecord();
        goldRecord.setUserId(user.getId());
        if(resource!=null){
            goldRecord.setResId(resource.getId());
            goldRecord.setResGold(resource.getResGold());
            goldRecord.setCurGold(user.getGold() + resource.getResGold());
            goldRecord.setResName(resource.getResName());
        }else{
            goldRecord.setCurGold(user.getGold() + gold);
        }
        goldRecord.setOriGold(user.getGold());
        goldRecord.setOpDesc(opDesc);
        goldRecord.setCreateBy(user.getId());//用户操作
        goldRecord.setCreateTime(ZonedDateTime.now());
        goldRecord.setUpdateBy(user.getId());
        goldRecord.setUpdateTime(ZonedDateTime.now());
        goldRecord.setDelFlag(0);
        Long id = goldRecordDao.createGoldRecord(goldRecord);//保存数据
        log.info("create gold record return id:{}",id);
        goldRecord.setId(id);
        return goldRecord;
    }
    /**
     * 积分减少
     * */
    public GoldRecord reduceGold(User user, Resource resource, int gold, String opDesc){//resourceDTO和gold 二者其一不为null即可
        GoldRecord goldRecord = new GoldRecord();
        goldRecord.setUserId(user.getId());
        if(resource!=null){
            goldRecord.setResId(resource.getId());
            goldRecord.setResGold(resource.getResGold());
            goldRecord.setCurGold(user.getGold() - resource.getResGold());
            goldRecord.setResName(resource.getResName());
        }else{
            goldRecord.setCurGold(user.getGold() - gold);
        }
        goldRecord.setOriGold(user.getGold());
        goldRecord.setOpDesc(opDesc);
        goldRecord.setCreateBy(user.getId());//用户操作
        goldRecord.setCreateTime(ZonedDateTime.now());
        goldRecord.setUpdateBy(user.getId());
        goldRecord.setUpdateTime(ZonedDateTime.now());
        goldRecord.setDelFlag(0);
        Long id = goldRecordDao.createGoldRecord(goldRecord);//保存数据
        log.info("create gold record return id:{}",id);
        goldRecord.setId(id);
        return goldRecord;
    }
}
