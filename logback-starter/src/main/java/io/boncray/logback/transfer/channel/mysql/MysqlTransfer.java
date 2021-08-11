package io.boncray.logback.transfer.channel.mysql;

import io.boncray.bean.mode.log.Log;
import io.boncray.bean.mode.log.LogType;
import io.boncray.bean.mode.log.RpcLog;
import io.boncray.cmdb.database.mybatis.SqlSessionDecorator;
import io.boncray.logback.transfer.channel.AbstractTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 00:43
 */
@Component
@ConditionalOnProperty(prefix = "log.manager", value = "transferChannel", havingValue = "MYSQL")
public class MysqlTransfer extends AbstractTransfer {

    @Autowired(required = false)
    @Qualifier("transferSqlSessionDecorator")
    private SqlSessionDecorator sqlSession;

    @Override
    public void transfer(Object data) {
        if (Collection.class.isAssignableFrom(data.getClass())) {
            Collection<?> collection = (Collection<?>) data;
            collection.stream()
                    .map(x -> (Log) x)
                    .findFirst()
                    .ifPresent(log -> {
                        sqlSession.insertBatch(MybatisConfig.mapperMethodMapping.get(log.logType().name()), collection);
                        if (log.logType() == LogType.RPC_LOG) {
                            updateNormalLog((Collection<RpcLog>) collection);
                        }
                    });

        } else {
            Log log = (Log) data;
            if (log.logType() == LogType.RPC_LOG) {
                updateNormalLog(Collections.singletonList((RpcLog) log));
            }
            sqlSession.insert(MybatisConfig.mapperMethodMapping.get(log.logType().name()), log);
        }
    }

    /**
     * 将ERROR级别的rpc日志，更新到Normal里面去
     */
    private void updateNormalLog(Collection<RpcLog> logs) {
        logs.stream()
                .filter(log -> "ERROR".equals(log.getLevel()))
                .forEach(log -> {
                    Map<String, Object> map = new HashMap<>(5);
                    map.put("level", log.getLevel());
                    map.put("parentTrackId", log.getParentTrackId());
                    map.put("currentTrackId", log.getCurrentTrackId());
                    sqlSession.update(MybatisConfig.mapperMethodMapping.get("normal_level_update"), map);
                });
    }


}