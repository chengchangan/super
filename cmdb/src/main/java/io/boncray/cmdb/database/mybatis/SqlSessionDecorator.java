package io.boncray.cmdb.database.mybatis;

import org.apache.ibatis.session.*;
import org.mybatis.spring.SqlSessionTemplate;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Sql执行器，基于SqlSessionTemplate.
 *
 * @author cca
 * @version 1.0
 * @date 2021/8/8 02:13
 */
public class SqlSessionDecorator {

    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 构造方法.
     *
     * @param sqlSessionTemplate sql模板类
     */
    public SqlSessionDecorator(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    /**
     * 获取模板.
     */
    public SqlSessionTemplate getSqlSessionTemplate() {
        return sqlSessionTemplate;
    }

    /**
     * 打开sqlsession.
     */
    public SqlSession openSession() {
        return this.sqlSessionTemplate.getSqlSessionFactory().openSession();
    }

    /**
     * 打开批量操作sqlsession.
     */
    public SqlSession openBatchSession() {
        return this.sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
    }

    /**
     * 查询单个，超过1个异常.
     *
     * @param statement 声明
     * @param <T>       类型
     */
    public <T> T selectOne(String statement) {
        return sqlSessionTemplate.selectOne(statement);
    }

    /**
     * 查询单个，超过1个异常.
     *
     * @param statement 声明
     * @param parameter 参数
     * @param <T>       类型
     */
    public <T> T selectOne(String statement, Object parameter) {
        return sqlSessionTemplate.selectOne(statement, parameter);
    }

    /**
     * 查询列表.
     *
     * @param statement 声明
     * @param <E>       类型
     */
    public <E> List<E> selectList(String statement) {
        return sqlSessionTemplate.selectList(statement);
    }

    /**
     * 查询列表.
     *
     * @param statement 声明
     * @param parameter 参数
     * @param <E>       类型
     */
    public <E> List<E> selectList(String statement, Object parameter) {
        return sqlSessionTemplate.selectList(statement, parameter);
    }


    /**
     * 查询Map.
     *
     * @param statement 声明
     * @param mapKey    mapKey
     * @param <K>       key
     * @param <V>       value
     */
    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return sqlSessionTemplate.selectMap(statement, mapKey);
    }

    /**
     * 查询Map.
     *
     * @param statement 声明
     * @param parameter 参数
     * @param mapKey    mapKey
     * @param <K>       key
     * @param <V>       value
     */
    public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
        return sqlSessionTemplate.selectMap(statement, parameter, mapKey);
    }

    /**
     * 查询.
     *
     * @param statement     声明
     * @param parameter     参数
     * @param resultHandler 结果处理器
     */
    public void select(String statement, Object parameter, ResultHandler resultHandler) {
        sqlSessionTemplate.select(statement, parameter, resultHandler);
    }

    /**
     * 查询.
     *
     * @param statement     声明
     * @param resultHandler 结果处理器
     */
    public void select(String statement, ResultHandler resultHandler) {
        sqlSessionTemplate.select(statement, resultHandler);
    }

    /**
     * 查询.
     *
     * @param statement     声明
     * @param parameter     参数
     * @param rowBounds     rowBounds
     * @param resultHandler 结果处理器
     */
    public void select(String statement, Object parameter, RowBounds rowBounds,
                       ResultHandler resultHandler) {
        sqlSessionTemplate.select(statement, parameter, rowBounds, resultHandler);
    }

    /**
     * 插入.
     *
     * @param statement 声明
     */
    public int insert(String statement) {
        return sqlSessionTemplate.insert(statement);
    }

    /**
     * 插入.
     *
     * @param statement 声明
     * @param parameter 参数
     */
    public int insert(String statement, Object parameter) {
        return sqlSessionTemplate.insert(statement, parameter);
    }

    /**
     * 批量插入.
     *
     * @param statement 声明
     * @param list      集合
     */
    public int insertBatch(String statement, Collection<?> list) {
        int count = 0;
        try (SqlSession sqlSession = openBatchSession()) {
            for (Object parameter : list) {
                count += sqlSession.insert(statement, parameter);
            }
            sqlSession.commit();
        }
        return count;
    }

    /**
     * 修改.
     *
     * @param statement 声明
     */
    public int update(String statement) {
        return sqlSessionTemplate.update(statement);
    }

    /**
     * 修改.
     *
     * @param statement 声明
     * @param parameter 参数
     */
    public int update(String statement, Object parameter) {
        return sqlSessionTemplate.update(statement, parameter);
    }

    /**
     * 修改.
     *
     * @param statement 声明
     * @param list      集合
     */
    public int updateBatch(String statement, Collection<?> list) {
        int count = 0;
        try (SqlSession sqlSession = openBatchSession()) {
            for (Object parameter : list) {
                count += sqlSession.update(statement, parameter);
            }
            sqlSession.commit();
        }
        return count;
    }

    /**
     * 删除.
     *
     * @param statement 声明
     */
    public int delete(String statement) {
        return sqlSessionTemplate.delete(statement);
    }

    /**
     * 删除.
     *
     * @param statement 声明
     * @param parameter 参数
     */
    public int delete(String statement, Object parameter) {
        return sqlSessionTemplate.delete(statement, parameter);
    }

    /**
     * 批量删除.
     *
     * @param statement 声明
     * @param list      集合
     */
    public int deleteBatch(String statement, Collection<?> list) {
        int count = 0;
        try (SqlSession sqlSession = openBatchSession()) {
            for (Object parameter : list) {
                count += sqlSession.delete(statement, parameter);
            }
            sqlSession.commit();
        }
        return count;
    }

    /**
     * 清除缓存.
     */
    public void clearCache() {
        sqlSessionTemplate.clearCache();
    }

    /**
     * 获取配置.
     */
    public Configuration getConfiguration() {
        return sqlSessionTemplate.getConfiguration();
    }

    /**
     * 获取mapper.
     *
     * @param clazz mapper定义类
     * @param <T>   mapper类
     */
    public <T> T getMapper(Class<T> clazz) {
        return sqlSessionTemplate.getMapper(clazz);
    }

    /**
     * 获取链接.
     */
    public Connection getConnection() {
        return sqlSessionTemplate.getConnection();
    }
}
