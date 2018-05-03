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
}
