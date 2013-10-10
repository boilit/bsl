package org.boilit.bsl.core;

/**
 * @author Boilit
 * @see
 */
public final class ArrayWrapper {
    private Object[] array;

    public ArrayWrapper(final Object[] array) {
        this.array = array;
    }

    public final Object get(final int index) {
        return array[index];
    }

    public final void set(final int index, Object value) {
        array[index] = value;
    }

    public final Object[] getArray() {
        return array;
    }
}
