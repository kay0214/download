package com.sandman.download.controller;

import com.sandman.download.entity.BaseDto;
import com.sandman.download.service.DownloadRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by sunpeikai on 2018/5/14.
 */
@RestController
@RequestMapping("/api/sandman/v1/downloadRecord")
public class DownloadRecordController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DownloadRecordService downloadRecordService;
    /**
     * 获取下载记录接口(分页)
     */
    @GetMapping("/getAllDownloadRecords")
    public BaseDto getAllDownloadRecords(Integer pageNumber, Integer size) {
        log.debug("REST request to get all DownloadRecords");
        Map data = null;
        try {
            data = downloadRecordService.getAllDownloadRecords(pageNumber, size);
        } catch (Exception e) {
            log.info("获取下载记录失败!异常:{}",e);
        }
        return new BaseDto(200,"请求成功!",data);
    }
}
