package com.sandman.download.controller;

import com.sandman.download.entity.BaseDto;
import com.sandman.download.service.GoldRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by wangj on 2018/5/14.
 */
@RestController
@RequestMapping("/api/sandman/v1/goldRecord")
public class GoldRecordController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoldRecordService goldRecordService;
    /**
     * 获取积分记录接口（分页）
     */
    @GetMapping("/getAllResourceRecord")
    public BaseDto getAllResourceRecords(Integer pageNumber, Integer size) {
        log.debug("REST request to get all ResourceRecords");
        Map data = null;
        try {
            data = goldRecordService.getAllResourceRecords(pageNumber, size);
        } catch (Exception e) {
            log.info("获取积分详情失败！异常:{}",e);
        }
        return new BaseDto(200,"请求成功!",data);
    }
}
