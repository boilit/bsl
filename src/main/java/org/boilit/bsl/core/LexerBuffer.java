package org.boilit.bsl.core;

/**
 * @author Boilit
 * @see
 */
public final class LexerBuffer {
    private int size;
    private char[] elements;

    public LexerBuffer() {
        this(16);
    }

    public LexerBuffer(final int initCapacity) {
        this.size = 0;
        this.elements = new char[initCapacity];
    }

    public final LexerBuffer append(final char c) {
        this.ensureCapacity(size + 1);
        this.elements[size++] = c;
        return this;
    }

    public final LexerBuffer append(final String string) {
        final int size = this.size, length = string.length();
        this.ensureCapacity(size + length);
        string.getChars(0, length, this.elements, size);
        this.size += length;
        return this;
    }

    public final LexerBuffer append(final char[] chars) {
        final int size = this.size, length = chars.length;
        this.ensureCapacity(size + length);
        System.arraycopy(chars, 0, this.elements, size, length);
        this.size += length;
        return this;
    }

    public final LexerBuffer append(final char[] chars, final int offset, int length) {
        if (offset < 0 || length < 0 || offset + length > chars.length) {
            throw new IndexOutOfBoundsException();
        }
        final int size = this.size;
        this.ensureCapacity(size + length);
        System.arraycopy(chars, offset, this.elements, size, length);
        this.size += length;
        return this;
    }

    public final LexerBuffer clear() {
        this.size = 0;
        return this;
    }

    @Override
    public final String toString() {
        return new String(this.elements, 0, size);
    }

    private final void ensureCapacity(final int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = ((elements.length * 3) >> 1) + 1;
            newCapacity = newCapacity < minCapacity ? minCapacity : newCapacity;
            char[] oldData = this.elements;
            this.elements = new char[newCapacity];
            System.arraycopy(oldData, 0, this.elements, 0, size);
        }
    }
}
