package com.mgp.ddemo.commons.exceptionhandler;

public class GlobalException extends RuntimeException {
    private Integer code;//异常码

    public GlobalException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
