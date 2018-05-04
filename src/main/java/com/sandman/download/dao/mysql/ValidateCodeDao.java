package com.sandman.download.dao.mysql;

import com.sandman.download.entity.ValidateCode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangj on 2018/5/4.
 */
@Repository
public interface ValidateCodeDao {
    public boolean createCode(ValidateCode validateCode);
    public void deleteByContact(String contact);//假删
    public ValidateCode findByContact(String contact);
    public void updateValidateCode(ValidateCode validateCode);
    public List<ValidateCode> getAllCodeInfo();
}
