package com.mgp.ddemo.commons.emun;

/**
 * 用于token
 */
public enum RequestStatus {

    LOGIN_FAIL(500, "登陆失败"),
    LOGIN_TOKEN(504,"token 不存在");

    private int code;
    private String msg;


    RequestStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "RequestStatus{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
