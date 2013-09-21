package org.boilit.bsl.xio;

import org.boilit.bsl.encoding.IEncoder;

import java.io.Writer;

/**
 * @author Boilit
 * @see
 */
public final class CharsPrinter implements IPrinter {
    private final Writer writer;
    private final IEncoder encoder;

    public CharsPrinter(final Writer writer, final IEncoder encoder) {
        this.writer = writer;
        this.encoder = encoder;
    }

    @Override
    public final int getPrinterKind() {
        return CHARS_PRINTER;
    }

    @Override
    public final void print(final byte[] bytes) throws Exception {
        encoder.write(writer, bytes);
    }

    @Override
    public final void print(final String string) throws Exception {
        encoder.write(writer, string);
    }

    @Override
    public final void print(final Object object) throws Exception {
        if (object == null) {
            return;
        }
        this.print(object.toString());
    }

    @Override
    public final void flush() throws Exception {
        writer.flush();
    }

    @Override
    public final void close() throws Exception {
        writer.close();
    }
}
