package org.boilit.bsl.formatter;

/**
 * @author Boilit
 * @see
 */
public final class FormatterManager {
    private int size;
    private Class[] classes;
    private IFormatter[] formatters;

    public FormatterManager() {
        this(8);
    }

    public FormatterManager(final int initialCapacity) {
        size = 0;
        classes = new Class[initialCapacity];
        formatters = new IFormatter[initialCapacity];
    }

    public final int getIndex(final Class key, final String format) {
        if (key == null) {
            return -1;
        }
        final Class[] classes = this.classes;
        final IFormatter[] formatters = this.formatters;
        for (int i = size - 1; i >= 0; i--) {
            if (classes[i].isAssignableFrom(key) && formatters[i].getFormat().equals(format)) {
                return i;
            }
        }
        return -1;
    }

    public final IFormatter add(final Class clazz, final IFormatter formatter) {
        final int index = this.getIndex(clazz, formatter.getFormat());
        if (index != -1) {
            return this.get(index);
        }
        final int size = this.size;
        this.ensureCapacity(size + 1);
        this.classes[size] = clazz;
        return this.formatters[this.size++] = formatter;
    }

    public final IFormatter get(final int index) {
        return formatters[index];
    }

    public final IFormatter set(final int index, final IFormatter value) {
        return formatters[index] = value;
    }

    public final int size() {
        return size;
    }

    public final FormatterManager clear() {
        for (int i = size - 1; i >= 0; i--) {
            classes[i] = null;
            formatters[i] = null;
        }
        size = 0;
        return this;
    }

    private final void ensureCapacity(final int minCapacity) {
        if (minCapacity > classes.length) {
            final int size = this.size;
            int newCapacity = ((classes.length * 3) >> 1) + 1;
            newCapacity = newCapacity < minCapacity ? minCapacity : newCapacity;
            final Class[] oldKeys = classes;
            classes = new Class[newCapacity];
            System.arraycopy(oldKeys, 0, classes, 0, size);
            final IFormatter[] oldValues = formatters;
            formatters = new IFormatter[newCapacity];
            System.arraycopy(oldValues, 0, formatters, 0, size);
        }
    }
}
