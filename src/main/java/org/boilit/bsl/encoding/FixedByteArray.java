package org.boilit.bsl.encoding;

/**
 * @author Boilit
 * @see
 */
public final class FixedByteArray {
    private int size;
    private byte[] bytes;

    public FixedByteArray() {
        this.bytes = new byte[0];
        this.size = 0;
    }

    public FixedByteArray(final int capacity) {
        this.bytes = new byte[capacity];
        this.size = 0;
    }

    public final FixedByteArray append(final byte b) {
        this.bytes[size++] = b;
        return this;
    }

    public final FixedByteArray append(final byte[] b) {
        final int length = b.length;
        System.arraycopy(b, 0, bytes, size, length);
        this.size += length;
        return this;
    }

    public final void dilatation(final int capacity) {
        if (capacity > bytes.length) {
            bytes = new byte[capacity];
        }
        size = 0;
    }

    public final int size() {
        return size;
    }

    public final byte[] bytes() {
        return bytes;
    }

    public final byte[] toArray() {
        byte[] b = new byte[size];
        System.arraycopy(bytes, 0, b, 0, size);
        return b;
    }
}
