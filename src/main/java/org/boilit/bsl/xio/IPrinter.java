package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public interface IPrinter {
    public static final int BYTES_PRINTER = 0;
    public static final int CHARS_PRINTER = 1;

    public int getPrinterKind();

    public void print(final byte[] bytes) throws Exception;

    public void print(final String string) throws Exception;

    public void print(final Object object) throws Exception;

    public void flush() throws Exception;

    public void close() throws Exception;
}
