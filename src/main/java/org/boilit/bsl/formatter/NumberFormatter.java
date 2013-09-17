package org.boilit.bsl.formatter;

import java.text.DecimalFormat;

/**
 * @author Boilit
 * @see
 */
public final class NumberFormatter extends AbstractFormatter {
    private final DecimalFormat decimalFormat;

    public NumberFormatter(final String format) {
        super(format);
        this.decimalFormat = new DecimalFormat(format);
    }

    @Override
    public String format(Object object) {
        return this.decimalFormat.format(object);
    }
}
