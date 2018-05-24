package com.sandman.download.service.user;

import com.sandman.download.dao.mysql.user.ValidateCodeDao;
import com.sandman.download.entity.common.BaseDto;
import com.sandman.download.entity.user.ValidateCode;
import com.sandman.download.service.common.SendEmailService;
import com.sandman.download.utils.RandomUtils;
import com.sandman.download.utils.TemplateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by sunpeikai on 2018/5/4.
 */
@Service
public class ValidateCodeService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private ValidateCodeDao validateCodeDao;
    @Autowired
    private SendEmailService sendEmailService;
    /**
     * 发送验证码，流程：先将基本信息保存到数据库。然后生成验证码并发送。然后保存验证码到数据库
     */
    public BaseDto sendValidateCode(ValidateCode validateCode) {
        log.debug("Request to send ValidateCode : {}", validateCode);
        if(validateCode.getContact()==null || "".equals(validateCode.getContact()))
            return new BaseDto(413,"请先填写联系方式!");
        //如果库里有这个联系方式的数据，就删除
        deleteByContact(validateCode.getContact());
        validateCode.setCode(RandomUtils.getValidateCode());//获取6位数随机码
        validateCode.setDeadLine(ZonedDateTime.now().plusMinutes(5));//有效时间5分钟

        validateCode.setIsValid(0);
        validateCode.setIsSend(0);
        validateCode.setCreateBy(1L);//系统创建
        validateCode.setCreateTime(ZonedDateTime.now());//创建时间
        validateCode.setUpdateBy(1L);
        validateCode.setUpdateTime(ZonedDateTime.now());
        validateCode.setDelFlag(0);

        validateCodeDao.createCode(validateCode);//保存到数据库
        validateCode = validateCodeDao.findByContact(validateCode.getContact());//获取到保存的验证码信息
        log.info("validateCode:{}",validateCode);
        boolean sendSuccess = false;
        if(validateCode.getContact().contains("@")){//如果包含@，则发送邮件验证码
            String emailContent = TemplateUtils.getTemplateByName("emailCode");
            emailContent = emailContent.replaceAll("\\$\\(recipient\\)",validateCode.getContact());//替换邮箱收件人
            emailContent = emailContent.replaceAll("\\$\\(emailCode\\)",validateCode.getCode());//替换验证码
            sendSuccess = sendEmailService.sendEmail("注册验证码",emailContent,validateCode.getContact());
            log.info("邮箱:[{}]已发送验证码[{}]",validateCode.getContact(),validateCode.getCode());
        }else{//发送手机验证码
            log.info("手机号:[{}]已发送验证码[{}]",validateCode.getContact(),validateCode.getCode());
            sendSuccess = true;//假如手机验证码发送成功
        }
        if(sendSuccess){
            validateCode.setIsValid(1);
            validateCode.setIsSend(1);
            log.info("validateCode:{}",validateCode);
            validateCodeDao.updateValidateCode(validateCode);
            return new BaseDto(200,"验证码发送成功");
        }

        return new BaseDto(414,"发送验证码失败");
    }

    /**
     * 查询全部
     * */
    public BaseDto getAllCodeInfo(){
        List<ValidateCode> list = validateCodeDao.getAllCodeInfo();
        list.forEach(validateCode -> {
            log.info(validateCode.toString());
        });
        return new BaseDto(200,"查询成功",list);
    }
    /**
     * 根据联系方式查询
     * */
    public ValidateCode findByContact(String contact){
        return validateCodeDao.findByContact(contact);
    }

    /**
     * delete by contact
     * */
    public void deleteByContact(String contact){
        validateCodeDao.deleteByContact(contact);
    }
}
