package org.boilit.bsl.formatter;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractFormatter implements IFormatter {
    private final String format;

    protected AbstractFormatter(final String format) {
        this.format = format;
    }

    @Override
    public final String getFormat() {
        return this.format;
    }
}
