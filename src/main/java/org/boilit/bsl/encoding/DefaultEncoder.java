package org.boilit.bsl.encoding;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * @author Boilit
 * @see
 */
public final class DefaultEncoder implements IEncoder {
    private final String encoding;

    public DefaultEncoder(final String encoding) {
        this.encoding = encoding;
    }

    @Override
    public final void write(final OutputStream outputStream, final byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }

    @Override
    public final void write(final OutputStream outputStream, final String string) throws IOException {
        outputStream.write(string.getBytes(this.encoding));
    }

    @Override
    public final void write(final Writer writer, final byte[] bytes) throws IOException {
        writer.write(new String(bytes, this.encoding));
    }

    @Override
    public final void write(final Writer writer, final String string) throws IOException {
        writer.write(string);
    }

    public final String getEncoding() {
        return encoding;
    }
}
