package com.ch.ch9.common;

import java.io.Serializable;

public class ApiResult<T> implements Serializable {
    private boolean success;
    private String code;
    private String message;
    private T data;

    // 无参构造
    public ApiResult() {
    }

    // 全参构造
    public ApiResult(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // Getter和Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 成功响应
    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setSuccess(true);
        result.setCode("SUCCESS");
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static ApiResult<Void> success() {
        return success(null);
    }

    public static <T> ApiResult<T> success(String message, T data) {
        ApiResult<T> result = success(data);
        result.setMessage(message);
        return result;
    }

    // 失败响应
    public static <T> ApiResult<T> error(String code, String message) {
        ApiResult<T> result = new ApiResult<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> ApiResult<T> error(String message) {
        return error("ERROR", message);
    }

    public static ApiResult<Void> success(String message) {
        ApiResult<Void> result = new ApiResult<>();
        result.setSuccess(true);
        result.setCode("SUCCESS");
        result.setMessage(message);
        return result;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}