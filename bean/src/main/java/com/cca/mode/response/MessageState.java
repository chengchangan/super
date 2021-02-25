package com.cca.mode.response;

/**
 * @author cca
 * @version 1.0
 * @date 2021/2/25 14:25
 */
public enum MessageState {

    SUCCESS(200, "成功"),
    FAILURE(102, "失败");

    private final int code;
    private final String msg;

    private MessageState(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }

}
