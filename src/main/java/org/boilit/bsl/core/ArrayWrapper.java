package org.boilit.bsl.core;

/**
 * @author Boilit
 * @see
 */
public final class ArrayWrapper {
    private final int length;
    private final Object[] array;

    public ArrayWrapper(final Object[] array) {
        this.array = array;
        this.length = array.length;
    }

    public final Object get(final int index) {
        return array[index];
    }

    public final void set(final int index, Object value) {
        array[index] = value;
    }

    public final int getLength() {
        return length;
    }

    public final Object[] getArray() {
        return array;
    }
}
