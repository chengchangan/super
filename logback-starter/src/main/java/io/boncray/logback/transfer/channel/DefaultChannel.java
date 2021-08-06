package io.boncray.logback.transfer.channel;

import cn.hutool.json.JSONUtil;
import io.boncray.logback.transfer.TransferAble;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 00:43
 */
public class DefaultChannel implements TransferAble {


    @Override
    public void transfer(Object data) {
        // todo 日志持久化
        System.err.println(JSONUtil.toJsonStr(data));
    }

}
