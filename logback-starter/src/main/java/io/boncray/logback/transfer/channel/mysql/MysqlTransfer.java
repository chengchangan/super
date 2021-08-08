package io.boncray.logback.transfer.channel.mysql;

import cn.hutool.json.JSONUtil;
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

    @Autowired
    @Qualifier("transferSqlSessionDecorator")
    private SqlSessionDecorator sqlSession;

    @Override
    public void transfer(Object data) {

        // todo 根据日志的类型，确定存放哪个表

        if (Collection.class.isAssignableFrom(data.getClass())) {
            // 如果批量，则使用批量保存 todo 待实现mapper mysql 持久化
//            sqlSession.insertBatch("XXX", (Collection<?>) data);
            System.out.println("insertBatch() :" + JSONUtil.toJsonStr(data));
        } else {
//            sqlSession.insert("XXX", data);
            System.out.println("insert() :" + JSONUtil.toJsonStr(data));
        }
    }


}