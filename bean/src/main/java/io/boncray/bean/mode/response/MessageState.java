package io.boncray.bean.mode.response;

/**
 * @author cca
 * @version 1.0
 * @date 2021/2/25 14:25
 */
public enum MessageState {

    FUSING(100, "熔断失败"),
    FAILURE(101, "失败"),
    SUCCESS(200, "成功"),
    UNKNOWN_ERROR(500, "内部未知错误");

    private final int code;
    private final String msg;

    private MessageState(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

}
