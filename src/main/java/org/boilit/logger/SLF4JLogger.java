package org.boilit.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Boilit
 * @see
 */
public final class SLF4JLogger extends AbstractLogger {
    private Logger logger;

    public SLF4JLogger() {
        super();
    }

    public SLF4JLogger(final String name) {
        super(name);
        logger = LoggerFactory.getLogger(name);
    }

    @Override
    public final boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public final void trace(final Throwable throwable, final String message, final Object... arguments) {
        if(throwable == null) {
            logger.trace(this.format(message, arguments));
        } else {
            logger.trace(this.format(message, arguments), throwable);
        }
    }

    @Override
    public final boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public final void debug(final Throwable throwable, final String message, final Object... arguments) {
        if(throwable == null) {
            logger.debug(this.format(message, arguments));
        } else {
            logger.debug(this.format(message, arguments), throwable);
        }
    }

    @Override
    public final boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public final void info(final Throwable throwable, final String message, final Object... arguments) {
        if(throwable == null) {
            logger.info(this.format(message, arguments));
        } else {
            logger.info(this.format(message, arguments), throwable);
        }
    }

    @Override
    public final boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public final void warn(final Throwable throwable, final String message, final Object... arguments) {
        if(throwable == null) {
            logger.warn(this.format(message, arguments));
        } else {
            logger.warn(this.format(message, arguments), throwable);
        }
    }

    @Override
    public final boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public final void error(final Throwable throwable, final String message, final Object... arguments) {
        if(throwable == null) {
            logger.error(this.format(message, arguments));
        } else {
            logger.error(this.format(message, arguments), throwable);
        }
    }
}
