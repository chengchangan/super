package io.boncray.cmdb.database.intercepter;


import cn.hutool.core.util.StrUtil;
import io.boncray.bean.mode.base.PageList;
import io.boncray.bean.mode.base.PageQuery;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * mybatis mysql 分页拦截器
 * 利用mysql SQL_CALC_FOUND_ROWS FOUND_ROWS 实现
 *
 * @author changan
 * @version 1.0
 * @date 2022/2/11 17:42
 */
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PageInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MapperMethod.ParamMap<Object> arg = (MapperMethod.ParamMap<Object>) invocation.getArgs()[1];
        PageQuery pageQuery = null;
        for (Object value : arg.values()) {
            if (value instanceof PageQuery) {
                pageQuery = (PageQuery) value;
                break;
            }
        }
        // 不是分页查询
        if (pageQuery == null) {
            return invocation.proceed();
        }


        Integer pageSize = pageQuery.getPageSize();
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }

        int start = 0;
        Integer currentPage = pageQuery.getPageIndex();
        if (currentPage > 1) {
            start = (currentPage - 1) * pageSize;
        }

        String sql = getSqlByInvocation(invocation);
        if (StrUtil.isEmpty(sql)) {
            return invocation.proceed();
        }

        sql = sql.trim();
        if (!sql.toLowerCase().startsWith("select")) {
            // todo
            throw new RuntimeException("非查询语句不能开启 ThreadPagingUtil 分页");
        }

        sql = "select SQL_CALC_FOUND_ROWS" + sql.substring(6);
        sql += " limit " + start + ", " + pageSize;

        resetSql2Invocation(invocation, sql);

        // 数据对象
        Object proceed = invocation.proceed();

        if (!(proceed instanceof List)) {
            throw new RuntimeException("分页查询必须返回 List 对象");
        }

        PageList<?> pageList = new PageList<>((List<?>) proceed);
        pageList.setPageSize(pageSize);
        pageList.setPageIndex(currentPage);
        if (pageList.size() > 0) {

            // 获取总数
            Executor ce = (Executor) invocation.getTarget();
            Transaction transaction = ce.getTransaction();
            ResultSet rs = transaction.getConnection().createStatement().executeQuery("select FOUND_ROWS()");
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
                break;
            }

            int left = count % pageSize;
            int totalPage = left == 0 ? count / pageSize : count / pageSize + 1;

            pageList.setTotal(count);
            pageList.setTotalPage(totalPage);
        } else {
            pageList.setTotal(0);
        }
        return pageList;
    }

    @Override
    public Object plugin(Object obj) {
        return Plugin.wrap(obj, this);
    }

    @Override
    public void setProperties(Properties arg0) {
        // doSomething
    }

    /**
     * 获取sql语句
     *
     * @param invocation
     * @return
     */
    private String getSqlByInvocation(Invocation invocation) {
        final Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        return boundSql.getSql();
    }

    /**
     * 包装sql后，重置到invocation中
     *
     * @param invocation
     * @param sql
     * @throws SQLException
     */
    private void resetSql2Invocation(Invocation invocation, String sql) throws SQLException {
        final Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = statement.getBoundSql(parameterObject);
        MappedStatement newStatement = newMappedStatement(statement, new BoundSqlSqlSource(boundSql));
        MetaObject msObject = MetaObject.forObject(newStatement, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        msObject.setValue("sqlSource.boundSql.sql", sql);
        args[0] = newStatement;
    }

    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    // 定义一个内部辅助类，作用是包装sq
    class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
