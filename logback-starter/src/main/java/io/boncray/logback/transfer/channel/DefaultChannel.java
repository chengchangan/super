package io.boncray.logback.transfer.channel;

import cn.hutool.json.JSONUtil;
import io.boncray.logback.transfer.Transferable;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 00:43
 */
public class DefaultChannel implements Transferable {


    @Override
    public void transfer(Object data) {
        // todo 日志持久化
        System.err.println(data.getClass().getName() + "_" + JSONUtil.toJsonStr(data));
    }

}
