package com.sandman.download.entity;

import java.time.ZonedDateTime;

public class ValidateCode {
    private Long id;
    private String contact;
    private String code;
    private ZonedDateTime deadLine;
    private int isValid;
    private int isSend;
    private Long createBy;
    private ZonedDateTime createTime;
    private Long updateBy;
    private ZonedDateTime updateTime;
    private int delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(ZonedDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public int getIsSend() {
        return isSend;
    }

    public void setIsSend(int isSend) {
        this.isSend = isSend;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "ValidateCode{" +
                "id=" + id +
                ", contact='" + contact + '\'' +
                ", code='" + code + '\'' +
                ", deadLine=" + deadLine +
                ", isValid=" + isValid +
                ", isSend=" + isSend +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
