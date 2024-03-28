package com.stonedt.spider.entity;

import lombok.Data;

@Data
public class ResponseResult<T> {

    public static final int OK_CODE = 200;

    public static final int FAIL_CODE = 501;

    private int code;

    private String msg;

    private T result;


    private ResponseResult(int code, String msg, T result) {
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public static <T> ResponseResult<T> success() {
        return new ResponseResult<>(OK_CODE, "成功", null);
    }
    public static <T> ResponseResult<T> success(T result) {
        return new ResponseResult<T>(OK_CODE, "成功", result);
    }
    public static <T> ResponseResult<T> successOnlyMsg(String msg) {
        return new ResponseResult<T>(OK_CODE, msg, null);
    }

    public static <T> ResponseResult<T> success(String msg, T result) {
        return new ResponseResult<>(OK_CODE, msg, result);
    }

    public static <T> ResponseResult<T> fail() {
        return new ResponseResult<>(FAIL_CODE, "失败", null);
    }

    public static <T> ResponseResult<T> fail(String msg) {
        return new ResponseResult<T>(FAIL_CODE, msg, null);
    }

    public static <T> ResponseResult<T> fail(String msg, T result) {
        return new ResponseResult<>(FAIL_CODE, msg, result);
    }

}