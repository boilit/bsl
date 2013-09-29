package org.boilit.bsl.encoding;

import org.boilit.bsl.xio.ByteArrayBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractEncoder implements IEncoder {
    private ThreadLocal<ByteArrayBuffer> localFixedByteArray;

    private final String encoding;

    public AbstractEncoder(final String encoding) {
        this.encoding = encoding;
        this.localFixedByteArray = new ThreadLocal<ByteArrayBuffer>();
        this.localFixedByteArray.set(new ByteArrayBuffer(1024));
    }

    @Override
    public final void write(final OutputStream outputStream, final byte[] bytes) throws IOException {
        outputStream.write(bytes);
    }

    @Override
    public final void write(final Writer writer, final byte[] bytes) throws IOException {
        writer.write(new String(bytes, encoding));
    }

    @Override
    public final void write(final Writer writer, final String string) throws IOException {
        writer.write(string);
    }

    public final String getEncoding() {
        return encoding;
    }

    protected final ByteArrayBuffer getFixedByteArray() {
        return localFixedByteArray.get();
    }
}
