package io.boncray.logback.transfer.channel.mysql;

import io.boncray.bean.mode.log.Log;
import io.boncray.core.database.mybatis.SqlSessionDecorator;
import io.boncray.logback.transfer.channel.AbstractTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/7 00:43
 */
@Component
//@ConditionalOnProperty() // todo 如果是 mysql 则装载此bean
public class MysqlTransfer extends AbstractTransfer {

    @Autowired(required = false)
    @Qualifier("transferSqlSessionDecorator")
    private SqlSessionDecorator sqlSession;

    @Override
    public void transfer(Object data) {
        if (Collection.class.isAssignableFrom(data.getClass())) {
            Collection<?> collection = (Collection<?>) data;
            try {
                collection.stream()
                        .map(x -> (Log) x)
                        .findFirst()
                        .ifPresent(log -> sqlSession.insertBatch(MybatisConfig.mapperMethodMapping.get(log.logType()), collection));
            }catch (Exception e){
                e.printStackTrace();
            }

        } else {
            Log log = (Log) data;
            sqlSession.insert(MybatisConfig.mapperMethodMapping.get(log.logType()), log);
        }
    }


}