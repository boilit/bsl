package org.boilit.logger;

/**
 * @author Boilit
 * @see
 */
public final class DefaultLogger extends AbstractLogger {

    public static final int LEVEL_TRACE = 1;
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_WARN = 4;
    public static final int LEVEL_ERROR = 5;

    private int level = LEVEL_ERROR;

    private boolean trace = false;
    private boolean debug = false;
    private boolean info = false;
    private boolean warn = false;
    private boolean error = true;

    public DefaultLogger() {
        super();
    }

    public DefaultLogger(final String name) {
        super(name);
    }

    public final int getLevel() {
        return level;
    }

    public final void setLevel(final int level) {
        this.trace = level <= LEVEL_TRACE;
        this.debug = level <= LEVEL_DEBUG;
        this.info = level <= LEVEL_INFO;
        this.warn = level <= LEVEL_WARN;
        this.error = level <= LEVEL_ERROR;
        this.level = level;
    }

    @Override
    public final boolean isTraceEnabled() {
        return trace;
    }

    @Override
    public final void trace(final Throwable throwable, final String message, final Object... arguments) {
        if (trace) {
            System.out.println(this.format(message, arguments));
            if (throwable != null) {
                throwable.printStackTrace(System.err);
            }
        }
    }

    @Override
    public final boolean isDebugEnabled() {
        return debug;
    }

    @Override
    public final void debug(final Throwable throwable, final String message, final Object... arguments) {
        if (debug) {
            System.out.println(this.format(message, arguments));
            if (throwable != null) {
                throwable.printStackTrace(System.err);
            }
        }
    }

    @Override
    public final boolean isInfoEnabled() {
        return info;
    }

    @Override
    public final void info(final Throwable throwable, final String message, final Object... arguments) {
        if (info) {
            System.out.println(this.format(message, arguments));
            if (throwable != null) {
                throwable.printStackTrace(System.err);
            }
        }
    }

    @Override
    public final boolean isWarnEnabled() {
        return warn;
    }

    @Override
    public final void warn(final Throwable throwable, final String message, final Object... arguments) {
        if (warn) {
            System.err.println(this.format(message, arguments));
            if (throwable != null) {
                throwable.printStackTrace(System.err);
            }
        }
    }

    @Override
    public final boolean isErrorEnabled() {
        return error;
    }

    @Override
    public final void error(final Throwable throwable, final String message, final Object... arguments) {
        if (error) {
            System.err.println(this.format(message, arguments));
            if (throwable != null) {
                throwable.printStackTrace(System.err);
            }
        }
    }
}
