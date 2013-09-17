package org.boilit.bsl.encoding;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Boilit
 * @see
 */
public final class Latin1Encoder extends AbstractEncoder {
    public Latin1Encoder(final String encoding) {
        super(encoding);
    }

    @Override
    public final void write(final OutputStream outputStream, final String string) throws IOException {
        final int n = string.length();
        final FixedByteArray fb = this.getFixedByteArray();
        fb.dilatation(n);
        for (int i = 0; i < n; i++) {
            encode(fb, string.charAt(i));
        }
        outputStream.write(fb.bytes(), 0, fb.size());
    }

    private final void encode(final FixedByteArray fb, final char c) {
        if (c < 0x100) {
            fb.append((byte) c);
        } else{
            fb.append((byte) '?');
        }
    }
    
//    private void decode(FixedCharArray fc, byte[] bytes) {
//        for(int i=0, n=bytes.length;i<n;i++) {
//            fc.append((char) (bytes[i] & 0xff));
//        }
//    }
}
