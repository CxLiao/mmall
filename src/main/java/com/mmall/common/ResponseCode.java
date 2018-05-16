package com.mmall.common;

/**
 * @author liaocx on 2017/10/16.
 */
public enum ResponseCode {
    /*
     * 返回给前端的状态码及对应的基本描述
     */
    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    /**
     * 构造器
     * @param code
     * @param desc
     */
    ResponseCode(int code, String desc){
        this.code = code;
        this.desc =desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
