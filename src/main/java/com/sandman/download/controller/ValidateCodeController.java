package com.sandman.download.controller;

import com.sandman.download.entity.BaseDto;
import com.sandman.download.entity.ValidateCode;
import com.sandman.download.service.ValidateCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangj on 2018/5/4.
 */
@RestController
@RequestMapping("/api/sandman/v1/validateCode")
public class ValidateCodeController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private ValidateCodeService validateCodeService;
    /**
     * POST  sendValidateCode : send a new validateCode.
     */
    @PostMapping("/sendValidateCode")
    public BaseDto sendValidateCode(@RequestBody ValidateCode validateCode){
        log.info("send a code to {}",validateCode.getContact());
        return validateCodeService.sendValidateCode(validateCode);
    }
}
