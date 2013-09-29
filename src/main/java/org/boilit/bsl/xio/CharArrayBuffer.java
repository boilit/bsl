package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public final class CharArrayBuffer {
    private int size;
    private char[] elements;

    public CharArrayBuffer() {
        this.elements = new char[16];
        this.size = 0;
    }

    public CharArrayBuffer(final int capacity) {
        this.elements = new char[capacity];
        this.size = 0;
    }

    public final CharArrayBuffer append(final char c) {
        this.ensureCapacity(size + 1);
        this.elements[size++] = c;
        return this;
    }

    public final CharArrayBuffer append(final char[] c) {
        final int length = c.length;
        this.ensureCapacity(size + length);
        System.arraycopy(c, 0, elements, size, length);
        this.size += length;
        return this;
    }

    public final CharArrayBuffer clear() {
        size = 0;
        return this;
    }

    public final int size() {
        return size;
    }

    public final char[] elements() {
        return elements;
    }

    public final char[] toArray() {
        final char[] c = new char[size];
        System.arraycopy(elements, 0, c, 0, size);
        return c;
    }

    private final void ensureCapacity(final int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = ((elements.length * 3) >> 1) + 1;
            newCapacity = newCapacity < minCapacity ? minCapacity : newCapacity;
            char[] oldData = this.elements;
            this.elements = new char[newCapacity];
            System.arraycopy(oldData, 0, this.elements, 0, oldData.length);
        }
    }
}
