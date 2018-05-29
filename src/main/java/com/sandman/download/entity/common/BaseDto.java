package com.sandman.download.entity.common;

/**
 * Created by sunpeikai on 2018/4/12.
 */
public class BaseDto {
    private int code;
    private String message;
    private Object data;
    /**
     * 空构造
     * */
    public BaseDto() {
    }
    /**
     * 参数为ResponseStatus的构造    -- 枚举类型参数
     * */
    public BaseDto(ResponseStatus responseStatus){
        this.code = responseStatus.getStatus();
        this.message = responseStatus.getReason();
    }
    /**
     * 参数为ResponseStatus和data的构造   -- 枚举类型参数
     * */
    public BaseDto(ResponseStatus responseStatus,Object data){
        this.code = responseStatus.getStatus();
        this.message = responseStatus.getReason();
        this.data = data;
    }
    /**
     * 参数为code，message的构造
     * */
    public BaseDto(int code, String message){
        this.code = code;
        this.message = message;
        this.data = null;
    }
    /**
     * 参数为code，message，data的构造
     * */
    public BaseDto(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
