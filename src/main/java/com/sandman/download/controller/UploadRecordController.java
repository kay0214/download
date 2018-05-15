package com.sandman.download.controller;

import com.sandman.download.entity.BaseDto;
import com.sandman.download.service.UploadRecordService;
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
@RequestMapping("/api/sandman/v1/uploadRecord")
public class UploadRecordController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UploadRecordService uploadRecordService;
    /**
     * 获取上传记录接口(分页)
     */
    @GetMapping("/getAllRecords")
    public BaseDto getAllUploadRecords(Integer pageNumber, Integer size) {
        log.debug("REST request to get all UploadRecords");
        Map data = uploadRecordService.getAllUploadRecords(pageNumber, size);
        if(data==null)
            return new BaseDto(422,"无数据");
        return new BaseDto(200,"请求成功!",data);
    }
}
