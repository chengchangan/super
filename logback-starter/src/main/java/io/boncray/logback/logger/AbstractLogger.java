package io.boncray.logback.logger;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.Marker;

/**
 * @author cca
 * @version 1.0
 * @date 2021/8/3 15:03
 */
public abstract class AbstractLogger implements Logger {

    /**
     * 获取日志
     */
    protected abstract Logger getLogger();

    protected abstract void putMDC();

    protected abstract void cleanMDC();

    protected void putMDCItem(String key, Object value) {
        if (ObjectUtil.isNotNull(key) && ObjectUtil.isNotNull(value)) {
            MDC.put(key, String.valueOf(value));
        }
    }

    @Override
    public String getName() {
        return getLogger().getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return getLogger().isTraceEnabled();
    }

    @Override
    public void trace(String s) {
        putMDC();
        getLogger().trace(s);
        cleanMDC();
    }

    @Override
    public void trace(String s, Object o) {
        putMDC();
        getLogger().trace(s, o);
        cleanMDC();
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        putMDC();
        getLogger().trace(s, o, o1);
        cleanMDC();
    }

    @Override
    public void trace(String s, Object... objects) {
        putMDC();
        getLogger().trace(s, objects);
        cleanMDC();
    }

    @Override
    public void trace(String s, Throwable throwable) {
        putMDC();
        getLogger().trace(s, throwable);
        cleanMDC();
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return getLogger().isTraceEnabled();
    }

    @Override
    public void trace(Marker marker, String s) {
        putMDC();
        getLogger().trace(marker, s);
        cleanMDC();
    }

    @Override
    public void trace(Marker marker, String s, Object o) {
        putMDC();
        getLogger().trace(marker, s, o);
        cleanMDC();
    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {
        putMDC();
        getLogger().trace(marker, s, o, o1);
        cleanMDC();
    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {
        putMDC();
        getLogger().trace(marker, s, objects);
        cleanMDC();
    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        putMDC();
        getLogger().trace(marker, s, throwable);
        cleanMDC();
    }

    @Override
    public boolean isDebugEnabled() {
        return getLogger().isDebugEnabled();
    }

    @Override
    public void debug(String s) {
        putMDC();
        getLogger().debug(s);
        cleanMDC();
    }

    @Override
    public void debug(String s, Object o) {
        putMDC();
        getLogger().debug(s, o);
        cleanMDC();
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        putMDC();
        getLogger().debug(s, o, o1);
        cleanMDC();
    }

    @Override
    public void debug(String s, Object... objects) {
        putMDC();
        getLogger().debug(s, objects);
        cleanMDC();
    }

    @Override
    public void debug(String s, Throwable throwable) {
        putMDC();
        getLogger().debug(s, throwable);
        cleanMDC();
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return getLogger().isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String s) {
        putMDC();
        getLogger().debug(marker, s);
        cleanMDC();
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
        putMDC();
        getLogger().debug(marker, s, o);
        cleanMDC();
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
        putMDC();
        getLogger().debug(marker, s, o, o1);
        cleanMDC();
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
        putMDC();
        getLogger().debug(marker, s, objects);
        cleanMDC();
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        putMDC();
        getLogger().debug(marker, s, throwable);
        cleanMDC();
    }

    @Override
    public boolean isInfoEnabled() {
        return getLogger().isInfoEnabled();
    }

    @Override
    public void info(String s) {
        putMDC();
        getLogger().info(s);
        cleanMDC();
    }

    @Override
    public void info(String s, Object o) {
        putMDC();
        getLogger().info(s, o);
        cleanMDC();
    }

    @Override
    public void info(String s, Object o, Object o1) {
        putMDC();
        getLogger().info(s, o, o1);
        cleanMDC();
    }

    @Override
    public void info(String s, Object... objects) {
        putMDC();
        getLogger().info(s, objects);
        cleanMDC();
    }

    @Override
    public void info(String s, Throwable throwable) {
        putMDC();
        getLogger().info(s, throwable);
        cleanMDC();
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return getLogger().isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String s) {
        putMDC();
        getLogger().info(marker, s);
        cleanMDC();
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        putMDC();
        getLogger().info(marker, s, o);
        cleanMDC();
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        putMDC();
        getLogger().info(marker, s, o, o1);
        cleanMDC();
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        putMDC();
        getLogger().info(marker, s, objects);
        cleanMDC();
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        putMDC();
        getLogger().info(marker, s, throwable);
        cleanMDC();
    }

    @Override
    public boolean isWarnEnabled() {
        return getLogger().isWarnEnabled();
    }

    @Override
    public void warn(String s) {
        putMDC();
        getLogger().warn(s);
        cleanMDC();
    }

    @Override
    public void warn(String s, Object o) {
        putMDC();
        getLogger().warn(s, o);
        cleanMDC();
    }

    @Override
    public void warn(String s, Object... objects) {
        putMDC();
        getLogger().warn(s, objects);
        cleanMDC();
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        putMDC();
        getLogger().warn(s, o, o1);
        cleanMDC();
    }

    @Override
    public void warn(String s, Throwable throwable) {
        putMDC();
        getLogger().warn(s, throwable);
        cleanMDC();
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return getLogger().isWarnEnabled();
    }

    @Override
    public void warn(Marker marker, String s) {
        putMDC();
        getLogger().warn(marker, s);
        cleanMDC();
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        putMDC();
        getLogger().warn(marker, s, o);
        cleanMDC();
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        putMDC();
        getLogger().warn(marker, s, o, o1);
        cleanMDC();
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        putMDC();
        getLogger().warn(marker, s, objects);
        cleanMDC();
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        putMDC();
        getLogger().warn(marker, s, throwable);
        cleanMDC();
    }

    @Override
    public boolean isErrorEnabled() {
        return getLogger().isErrorEnabled();
    }

    @Override
    public void error(String s) {
        putMDC();
        getLogger().error(s);
        cleanMDC();
    }

    @Override
    public void error(String s, Object o) {
        putMDC();
        getLogger().error(s, o);
        cleanMDC();
    }

    @Override
    public void error(String s, Object o, Object o1) {
        putMDC();
        getLogger().error(s, o, o1);
        cleanMDC();
    }

    @Override
    public void error(String s, Object... objects) {
        putMDC();
        getLogger().error(s, objects);
        cleanMDC();
    }

    @Override
    public void error(String s, Throwable throwable) {
        putMDC();
        getLogger().error(s, throwable);
        cleanMDC();
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return getLogger().isErrorEnabled();
    }

    @Override
    public void error(Marker marker, String s) {
        putMDC();
        getLogger().error(marker, s);
        cleanMDC();
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        putMDC();
        getLogger().error(marker, s, o);
        cleanMDC();
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        putMDC();
        getLogger().error(marker, s, o, o1);
        cleanMDC();
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        putMDC();
        getLogger().error(marker, s, objects);
        cleanMDC();
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        putMDC();
        getLogger().error(marker, s, throwable);
        cleanMDC();
    }


}
