package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * 服务端响应对象
 * @author liaocx on 2017/10/16.
 *
 * JsonSerialize注解保证序列化json的时候，如果是null的对象，key也会消失
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    /**
     * 私有的构造器1
     */
    private ServerResponse(int status){
        this.status = status;
    }

    /**
     * 私有的构造器2
     */
    private ServerResponse(int status, T data){
        this.status = status;
        this.data = data;
    }

    /**
     * 私有的构造器3
     */
    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 私有的构造器4
     */
    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * return this.status == 0
     * @return
     *
     * JsonIgnore注解使之不在json序列化结果当中
     */
    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    /**
     * getter方法
     */
    public int getStatus() {
        return status;
    }
    public T getData() {
        return data;
    }
    public String getMsg() {
        return msg;
    }

    /**
     * success
     *  下方2、4两个范型方法解决了当T是String时构造器不能分辨到底是传data还是传msg
     *
     */
    public static <T> ServerResponse<T> createBySuccess() { //对应私有构造器1
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> createBySuccess(T data) { //对应私有构造器2
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponse<T> createBySuccess(String msg, T data) { //对应私有构造器3
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServerResponse<T> createBySuccessMessage(String msg) { //对应私有构造器4
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    /**
     * fail
     */
    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
        //返回给前端错误的信息和对应的status
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new ServerResponse<T>(errorCode,errorMessage);
    }
}