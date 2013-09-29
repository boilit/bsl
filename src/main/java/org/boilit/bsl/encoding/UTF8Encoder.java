package org.boilit.bsl.encoding;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Boilit
 * @see
 */
public final class UTF8Encoder extends AbstractEncoder {

    public UTF8Encoder(final String encoding) {
        super(encoding);
    }

    @Override
    public final void write(final OutputStream outputStream, final String string) throws IOException {
        final int n = string.length();
        final ByteArrayBuffer fb = this.getFixedByteArray().clear();
        for (int i = 0; i < n; i++) {
            encode(fb, string.charAt(i));
        }
        outputStream.write(fb.elements(), 0, fb.size());
    }

    /**
     * encode result same as jdk String.getBytes("UTF-8")
     * @param fb
     * @param c
     */
    private final void encode(final ByteArrayBuffer fb, final char c) {
        if (c < 0x80) {
            fb.append((byte) c);
        } else if (c < 0x800) {
            fb.append((byte) (0xc0 | c >> 6));
            fb.append((byte) (0x80 | c & 0x3f));
        } else if(c < 0xD800){
            fb.append((byte) (0xe0 | c >> 12));
            fb.append((byte) (0x80 | c >> 6 & 0x3f));
            fb.append((byte) (0x80 | c & 0x3f));
        } else if(c < 0xE000){
            fb.append((byte) 0x3F);
        } else if (c < 0x10000) {
            fb.append((byte) (0xe0 | c >> 12));
            fb.append((byte) (0x80 | c >> 6 & 0x3f));
            fb.append((byte) (0x80 | c & 0x3f));
        } else if (c < 0x200000) {
            fb.append((byte) (0xf0 | c >> 18));
            fb.append((byte) (0x80 | c >> 12));
            fb.append((byte) (0x80 | c >> 6 & 0x3f));
            fb.append((byte) (0x80 | c & 0x3f));
        } else if (c < 0x4000000) {
            fb.append((byte) (0xf8 | c >> 24));
            fb.append((byte) (0x80 | c >> 18));
            fb.append((byte) (0x80 | c >> 12));
            fb.append((byte) (0x80 | c >> 6 & 0x3f));
            fb.append((byte) (0x80 | c & 0x3f));
        } else {
            fb.append((byte) (0xfc | c >> 30));
            fb.append((byte) (0x80 | c >> 24));
            fb.append((byte) (0x80 | c >> 18));
            fb.append((byte) (0x80 | c >> 12));
            fb.append((byte) (0x80 | c >> 6 & 0x3f));
            fb.append((byte) (0x80 | c & 0x3f));
        }
    }
}
