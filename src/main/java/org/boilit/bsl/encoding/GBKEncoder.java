package org.boilit.bsl.encoding;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author Boilit
 * @see
 */
public final class GBKEncoder extends AbstractEncoder {
    public static byte[][] TABLE_GBK = null;

    public GBKEncoder(final String encoding) {
        super(encoding);
        if (TABLE_GBK != null) {
            return;
        }
        TABLE_GBK = new byte[Character.MAX_VALUE + 1][];
        final int n = TABLE_GBK.length;
        for (int i = 0; i < n; i++) {
            try {
                TABLE_GBK[i] = String.valueOf((char) i).getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public final void write(final OutputStream outputStream, final String string) throws IOException {
        final byte[][] table = GBKEncoder.TABLE_GBK;
        final int n = string.length();
        final ByteArrayBuffer fb = this.getFixedByteArray().clear();
        for (int i = 0; i < n; i++) {
            fb.append(table[string.charAt(i)]);
        }
        outputStream.write(fb.elements(), 0, fb.size());
    }
}
