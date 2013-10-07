package org.boilit.bsl.core;

/**
 * @author Boilit
 * @see
 */
public final class ArrayWrapper {
    private static ThreadLocal<ArrayWrapper> local = new ThreadLocal<ArrayWrapper>();

    private Object[] array;

    public ArrayWrapper(final Object[] array) {
        this.array = array;
    }

    public static final ArrayWrapper wrap(final Object[] array) {
        final ArrayWrapper wrapper = local.get();
        if(wrapper != null) {
            wrapper.array = array;
            return wrapper;
        }
        final ArrayWrapper next = new ArrayWrapper(array);
        local.set(next);
        return next;
    }

    public final Object get(final int index) {
        return array[index];
    }

    public final void set(final int index, Object value) {
        array[index] = value;
    }

    public Object[] getArray() {
        return array;
    }
}
