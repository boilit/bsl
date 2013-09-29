package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public final class ByteArrayBuffer {
    private int size;
    private byte[] elements;

    public ByteArrayBuffer() {
        this.elements = new byte[0];
        this.size = 0;
    }

    public ByteArrayBuffer(final int capacity) {
        this.elements = new byte[capacity];
        this.size = 0;
    }

    public final ByteArrayBuffer append(final byte b) {
        this.ensureCapacity(size + 1);
        this.elements[size++] = b;
        return this;
    }

    public final ByteArrayBuffer append(final byte[] b) {
        final int length = b.length;
        this.ensureCapacity(size + length);
        System.arraycopy(b, 0, elements, size, length);
        this.size += length;
        return this;
    }

    public final ByteArrayBuffer clear() {
        size = 0;
        return this;
    }

    public final int size() {
        return size;
    }

    public final byte[] elements() {
        return elements;
    }

    public final byte[] toArray() {
        byte[] b = new byte[size];
        System.arraycopy(elements, 0, b, 0, size);
        return b;
    }

    private final void ensureCapacity(final int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = ((elements.length * 3) >> 1) + 1;
            newCapacity = newCapacity < minCapacity ? minCapacity : newCapacity;
            byte[] oldData = this.elements;
            this.elements = new byte[newCapacity];
            System.arraycopy(oldData, 0, this.elements, 0, oldData.length);
        }
    }
}
