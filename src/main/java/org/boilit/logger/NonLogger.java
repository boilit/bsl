package org.boilit.logger;

/**
 * @author Boilit
 * @see
 */
public final class NonLogger extends AbstractLogger {

    public NonLogger() {
        super();
    }

    public NonLogger(final String name) {
        super(name);
    }

    @Override
    public final boolean isTraceEnabled() {
        return false;
    }

    @Override
    public final void trace(final Throwable throwable, final String message, final Object... arguments) {
    }

    @Override
    public final boolean isDebugEnabled() {
        return false;
    }

    @Override
    public final void debug(final Throwable throwable, final String message, final Object... arguments) {
    }

    @Override
    public final boolean isInfoEnabled() {
        return false;
    }

    @Override
    public final void info(final Throwable throwable, final String message, final Object... arguments) {
    }

    @Override
    public final boolean isWarnEnabled() {
        return false;
    }

    @Override
    public final void warn(final Throwable throwable, final String message, final Object... arguments) {
    }

    @Override
    public final boolean isErrorEnabled() {
        return false;
    }

    @Override
    public final void error(final Throwable throwable, final String message, final Object... arguments) {
    }
}
