package org.boilit.bsl.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Boilit
 * @see
 */
public final class DateFormatter extends AbstractFormatter {
    private final SimpleDateFormat simpleDateFormat;

    public DateFormatter(final String format) {
        super(format);
        this.simpleDateFormat = new SimpleDateFormat(format);
    }

    @Override
    public String format(Object object) {
        return this.simpleDateFormat.format((Date) object);
    }
}
