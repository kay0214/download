package com.sandman.download.entity;

import java.time.ZonedDateTime;

public class GoldRecord {
    private Long id;
    private Long userId;
    private Long resId;
    private String resName;
    private int oriGold;
    private int resGold;
    private int curGold;
    private String opDesc;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getOriGold() {
        return oriGold;
    }

    public void setOriGold(int oriGold) {
        this.oriGold = oriGold;
    }

    public int getResGold() {
        return resGold;
    }

    public void setResGold(int resGold) {
        this.resGold = resGold;
    }

    public int getCurGold() {
        return curGold;
    }

    public void setCurGold(int curGold) {
        this.curGold = curGold;
    }

    public String getOpDesc() {
        return opDesc;
    }

    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc;
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
        return "GoldRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", resId=" + resId +
                ", resName='" + resName + '\'' +
                ", oriGold=" + oriGold +
                ", resGold=" + resGold +
                ", curGold=" + curGold +
                ", opDesc='" + opDesc + '\'' +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
