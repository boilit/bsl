package org.boilit.logger;

/**
 * @author Boilit
 * @see
 */
public interface ILogger {

    public static final String LOGGER_NAME = "ROOT";

    public boolean isTraceEnabled();

    public void trace(String message);

    public void trace(String message, Object... arguments);

    public void trace(Throwable throwable, String message);

    public void trace(Throwable throwable, String message, Object... arguments);

    public boolean isDebugEnabled();

    public void debug(String message);

    public void debug(String message, Object... arguments);

    public void debug(Throwable throwable, String message);

    public void debug(Throwable throwable, String message, Object... arguments);

    public boolean isInfoEnabled();

    public void info(String message);

    public void info(String message, Object... arguments);

    public void info(Throwable throwable, String message);

    public void info(Throwable throwable, String message, Object... arguments);

    public boolean isWarnEnabled();

    public void warn(String message);

    public void warn(String message, Object... arguments);

    public void warn(Throwable throwable, String message);

    public void warn(Throwable throwable, String message, Object... arguments);

    public boolean isErrorEnabled();

    public void error(String message);

    public void error(String message, Object... arguments);

    public void error(Throwable throwable, String message);

    public void error(Throwable throwable, String message, Object... arguments);
}
