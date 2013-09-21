package org.boilit.bsl.xio;

import org.boilit.bsl.encoding.IEncoder;

import java.io.OutputStream;

/**
 * @author Boilit
 * @see
 */
public final class BytesPrinter implements IPrinter {
    private final OutputStream outputStream;
    private final IEncoder encoder;

    public BytesPrinter(final OutputStream outputStream, final IEncoder encoder) {
        this.outputStream = outputStream;
        this.encoder = encoder;
    }

    @Override
    public final int getPrinterKind() {
        return BYTES_PRINTER;
    }

    @Override
    public final void print(final byte[] bytes) throws Exception  {
        encoder.write(outputStream, bytes);
    }

    @Override
    public final void print(final String string) throws Exception {
        encoder.write(outputStream, string);
    }

    @Override
    public final void print(final Object object) throws Exception  {
        if(object == null) {
            return;
        }
        this.print(object.toString());
    }

    @Override
    public final void flush() throws Exception  {
        outputStream.flush();
    }

    @Override
    public final void close() throws Exception  {
        outputStream.close();
    }
}
