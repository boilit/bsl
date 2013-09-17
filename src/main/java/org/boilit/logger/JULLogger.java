package org.boilit.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Boilit
 * @see
 */
public final class JULLogger extends AbstractLogger {
    private Logger logger;

    public JULLogger() {
        super();
    }

    public JULLogger(final String name) {
        super(name);
        logger = Logger.getLogger(name);
    }

    @Override
    public final boolean isTraceEnabled() {
        return logger.isLoggable(Level.FINER);
    }

    @Override
    public final void trace(final Throwable throwable, final String message, final Object... arguments) {
        final Logger logger = this.logger;
        final Level level = Level.FINER;
        if (logger.isLoggable(level)) {
            if (throwable == null) {
                logger.log(level, this.format(message, arguments));
            } else {
                logger.log(level, this.format(message, arguments), throwable);
            }
        }
    }

    @Override
    public final boolean isDebugEnabled() {
        return logger.isLoggable(Level.FINE);
    }

    @Override
    public final void debug(final Throwable throwable, final String message, final Object... arguments) {
        final Logger logger = this.logger;
        final Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            if (throwable == null) {
                logger.log(level, this.format(message, arguments));
            } else {
                logger.log(level, this.format(message, arguments), throwable);
            }
        }
    }

    @Override
    public final boolean isInfoEnabled() {
        return logger.isLoggable(Level.INFO);
    }

    @Override
    public final void info(final Throwable throwable, final String message, final Object... arguments) {
        final Logger logger = this.logger;
        final Level level = Level.INFO;
        if (logger.isLoggable(level)) {
            if (throwable == null) {
                logger.log(level, this.format(message, arguments));
            } else {
                logger.log(level, this.format(message, arguments), throwable);
            }
        }
    }

    @Override
    public final boolean isWarnEnabled() {
        return logger.isLoggable(Level.WARNING);
    }

    @Override
    public final void warn(final Throwable throwable, final String message, final Object... arguments) {
        final Logger logger = this.logger;
        final Level level = Level.WARNING;
        if (logger.isLoggable(level)) {
            if (throwable == null) {
                logger.log(level, this.format(message, arguments));
            } else {
                logger.log(level, this.format(message, arguments), throwable);
            }
        }
    }

    @Override
    public final boolean isErrorEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }

    @Override
    public final void error(final Throwable throwable, final String message, final Object... arguments) {
        final Logger logger = this.logger;
        final Level level = Level.SEVERE;
        if (logger.isLoggable(level)) {
            if (throwable == null) {
                logger.log(level, this.format(message, arguments));
            } else {
                logger.log(level, this.format(message, arguments), throwable);
            }
        }
    }
}
