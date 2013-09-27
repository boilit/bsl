package org.boilit.bsl.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
@SuppressWarnings("unchecked")
public final class ExecuteVariant {
    private int size;
    private String[] keys;
    private Object[] values;

    public ExecuteVariant() {
        this(8);
    }

    public ExecuteVariant(final int initialCapacity) {
        size = 0;
        keys = new String[initialCapacity];
        values = new Object[initialCapacity];
    }

    public final int getIndex(final String key) {
        if (key == null) {
            return -1;
        }
        final String[] keys = this.keys;
        for (int i = size - 1; i >= 0; i--) {
            if (key.equals(keys[i])) {
                return i;
            }
        }
        return -1;
    }

    public final Object add(final String key, final Object value) {
        final int size = this.size;
        this.ensureCapacity(size + 1);
        this.keys[size] = key;
        return this.values[this.size++] = value;
    }

    public final Object get(final int index) {
        return values[index];
    }

    public final Object set(final int index, final Object value) {
        return values[index] = value;
    }

    public final ExecuteVariant clear() {
        for (int i = size - 1; i >= 0; i--) {
            keys[i] = null;
            values[i] = null;
        }
        size = 0;
        return this;
    }

    public final ExecuteVariant accept(final Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return this;
        }
        Map.Entry<String, Object> entry;
        final Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            entry = it.next();
            this.add(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public final Map<String, Object> toMap() {
        final int n = size;
        final String[] keys = this.keys;
        final Object[] values = this.values;
        final Map<String, Object> map = new HashMap<String, Object>(size);
        for (int i = 0; i < n; i++) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    private final void ensureCapacity(final int minCapacity) {
        if (minCapacity > keys.length) {
            int newCapacity = ((keys.length * 3) >> 1) + 1;
            newCapacity = newCapacity < minCapacity ? minCapacity : newCapacity;
            final String[] oldKeys = keys;
            keys = new String[newCapacity];
            System.arraycopy(oldKeys, 0, keys, 0, oldKeys.length);
            final Object[] oldValues = values;
            values = new Object[newCapacity];
            System.arraycopy(oldValues, 0, values, 0, oldValues.length);
        }
    }
}
