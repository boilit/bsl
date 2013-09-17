package org.boilit.bsl.encoding;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * @author Boilit
 * @see
 */
public interface IEncoder {

    public void write(OutputStream outputStream, byte[] bytes) throws IOException;

    public void write(OutputStream outputStream, String string) throws IOException;

    public void write(Writer writer, byte[] bytes) throws IOException;

    public void write(Writer writer, String string) throws IOException;
}
