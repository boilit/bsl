package org.boilit.bsl.encoding;

import org.boilit.bsl.xio.ByteArrayBuffer;

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
        synchronized (GBKEncoder.class) {
            if (TABLE_GBK != null) {
                return;
            }
            TABLE_GBK = new byte[Character.MAX_VALUE + 1][];
            for (int i = Character.MAX_VALUE; i>=0; i--) {
                try {
                    TABLE_GBK[i] = String.valueOf((char) i).getBytes("GBK");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
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
