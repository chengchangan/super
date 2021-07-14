package io.boncray.bean.mode.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cca
 * @version 1.0
 * @date 2021/2/25 14:23
 */
@Data
public class Result<T> implements Serializable {

    @ApiModelProperty("业务数据返回")
    private T data;

    @ApiModelProperty(value = "成功/失败", required = true)
    private Boolean success = true;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty(value = "状态码", required = true)
    private Integer status;

    public Result() {

    }

    public Result(T data, Boolean success, String msg, Integer status) {
        this.data = data;
        this.success = success;
        this.msg = msg;
        this.status = status;
    }

    public static <T> Result<T> success() {
        return new Result<>(null, true, MessageState.SUCCESS.getMsg(), MessageState.SUCCESS.getCode());
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data, true, MessageState.SUCCESS.getMsg(), MessageState.SUCCESS.getCode());
    }

    public static <T> Result<T> failure() {
        return new Result<>(null, false, MessageState.FAILURE.getMsg(), MessageState.FAILURE.getCode());
    }

    /**
     * 熔断失败
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> fusingFailure() {
        return new Result<>(null, false, MessageState.FUSING.getMsg(), MessageState.FUSING.getCode());
    }

    public static <T> Result<T> failure(MessageState messageState) {
        return new Result<>(null, false, messageState.getMsg(), messageState.getCode());
    }

    public static <T> Result<T> failure(Integer code, String msg) {
        return new Result<>(null, false, msg, code);
    }

}

