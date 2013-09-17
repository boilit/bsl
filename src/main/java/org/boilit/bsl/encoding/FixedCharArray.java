package org.boilit.bsl.encoding;

/**
 * @author Boilit
 * @see
 */
public final class FixedCharArray {
    private int size;
    private char[] chars;

    public FixedCharArray() {
        this.chars = new char[0];
        this.size = 0;
    }

    public FixedCharArray(final int capacity) {
        this.chars = new char[capacity];
        this.size = 0;
    }

    public final FixedCharArray append(final char c) {
        this.chars[size++] = c;
        return this;
    }

    public final FixedCharArray append(final char[] c) {
        final int length = c.length;
        System.arraycopy(c, 0, chars, size, length);
        this.size += length;
        return this;
    }

    public final void dilatation(final int capacity) {
        if (capacity > chars.length) {
            chars = new char[capacity];
        }
        size = 0;
    }

    public final int size() {
        return size;
    }

    public final char[] chars() {
        return chars;
    }

    public final char[] toArray() {
        final char[] c = new char[size];
        System.arraycopy(chars, 0, c, 0, size);
        return c;
    }
}
