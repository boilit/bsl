package org.boilit.logger;

/**
 * @author Boilit
 * @see
 */
@SuppressWarnings("unchecked")
public abstract class AbstractLogger implements ILogger {
    private static final Object[] DEFAULT_ARGUMENTS = new Object[0];
    private String name;

    public AbstractLogger() {
        this.name = LOGGER_NAME;
    }

    public AbstractLogger(final String name) {
        this.name = name;
    }

    @Override
    public final void trace(final String message) {
        this.trace(message, DEFAULT_ARGUMENTS);
    }

    @Override
    public final void trace(final String message, final Object... arguments) {
        this.trace(null, message, arguments);
    }

    @Override
    public final void trace(final Throwable throwable, final String message) {
        this.trace(throwable, message, DEFAULT_ARGUMENTS);
    }

    @Override
    public final void debug(final String message) {
        this.debug(message, DEFAULT_ARGUMENTS);
    }

    @Override
    public final void debug(final String message, final Object... arguments) {
        this.debug(null, message, arguments);
    }

    @Override
    public final void debug(final Throwable throwable, final String message) {
        this.debug(throwable, message, DEFAULT_ARGUMENTS);
    }

    @Override
    public final void info(final String message) {
        this.info(message, DEFAULT_ARGUMENTS);
    }

    @Override
    public final void info(final String message, final Object... arguments) {
        this.info(null, message, arguments);
    }

    @Override
    public final void info(final Throwable throwable, final String message) {
        this.info(throwable, message, DEFAULT_ARGUMENTS);
    }

    @Override
    public final void warn(final String message) {
        this.warn(message, DEFAULT_ARGUMENTS);
    }

    @Override
    public final void warn(final String message, final Object... arguments) {
        this.warn(null, message, arguments);
    }

    @Override
    public final void warn(final Throwable throwable, final String message) {
        this.warn(throwable, message, DEFAULT_ARGUMENTS);
    }

    @Override
    public final void error(final String message) {
        this.error(message, DEFAULT_ARGUMENTS);
    }

    @Override
    public final void error(final String message, final Object... arguments) {
        this.error(null, message, arguments);
    }

    @Override
    public final void error(final Throwable throwable, final String message) {
        this.error(throwable, message, DEFAULT_ARGUMENTS);
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    protected final String format(final String message, final Object... arguments) {
        if (message == null) {
            return "";
        }
        if (arguments == null || arguments.length == 0) {
            return message;
        }
        char c;
        int index = 0, srcIndex = -1, dstIndex = -1;
        StringBuilder builder = new StringBuilder(message.length());
        for (int i = 0, n = message.length(); i < n; i++) {
            c = message.charAt(i);
            switch (c) {
                case '{':
                    srcIndex = i;
                    dstIndex = builder.length();
                    builder.append(c);
                    break;
                case '}':
                    if (srcIndex == -1) {
                        builder.append(c);
                    } else if (srcIndex + 1 == i || message.substring(srcIndex + 1, i).trim().length() == 0) {
                        if (index < arguments.length) {
                            builder.delete(dstIndex, builder.length());
                            builder.append(this.toString(arguments[index++]));
                        }
                        srcIndex = -1;
                    }
                    break;
                default:
                    builder.append(c);
            }
        }
        return builder.toString();
    }

    private final String toString(final Object object) {
        return object == null ? "" : object.toString();
    }
}
