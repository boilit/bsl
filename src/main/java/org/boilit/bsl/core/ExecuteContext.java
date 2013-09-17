package org.boilit.bsl.core;

import org.boilit.bsl.xio.IPrinter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class ExecuteContext {
    public static final int CONTROL_GOON = 0xf0;
    public static final int CONTROL_NEXT = 0x01;
    public static final int CONTROL_BREAK = 0x03;

    private boolean goon = true;
    private int control = CONTROL_GOON;

    private int size;
    private int index;
    private ExecuteVariant[] elements;

    private IPrinter printer;

    public ExecuteContext(final IPrinter printer) {
        this(null, printer);
    }

    public ExecuteContext(final Map<String, Object> model, final IPrinter printer) {
        size = 0;
        index = 0;
        elements = new ExecuteVariant[8];
        if (model != null) {
            this.occupy(model);
        }
        this.occupy();
        this.printer = printer;
    }

    public final boolean isGoon() {
        return goon;
    }

    public final int getControl() {
        return control;
    }

    public final int setControl(final int control) {
        switch (control) {
            case CONTROL_NEXT:
                goon = false;
                break;
            case CONTROL_BREAK:
                goon = false;
                break;
            default:
                goon = true;
        }
        return this.control = control;
    }

    public final IPrinter getPrinter() {
        return printer;
    }

    public final ExecuteVariant occupy() {
        return this.occupy(null);
    }

    private final ExecuteVariant occupy(final Map<String, Object> model) {
        if (index < size) {
            return elements[index++];
        }
        this.ensureCapacity(++size);
        return elements[index++] = new ExecuteVariant().accept(model);
    }

    public final void revert() {
        elements[--index].clear();
    }

    public void clear() {
        for(int i=size - 1; i>=0;i--) {
            elements[i].clear();
        }
        size = 0;
        index = 0;
    }

    public final int getVariableIndex(final String key) {
        final ExecuteVariant[] elements = this.elements;
        int i = index - 1, j;
        for (; i >= 0; i--) {
            j = elements[i].getIndex(key);
            if (j != -1) {
                return (i << 16) | j;
            }
        }
        return -1;
    }

    public final Object addVariable(final String key, final Object value) {
        return elements[index - 1].add(key, value);
    }

    public final Object addReturned(final String key, final Object value) {
        if (index == 0) {
            return null;
        }
        return elements[0].add(key, value);
    }

    public final Object getVariable(final int index) {
        return elements[index >>> 16].get(index & 0xff);
    }

    public final Object setVariable(final int index, final Object value) {
        return elements[index >>> 16].set(index & 0xff, value);
    }

    public final Map<String, Object> toMap() {
        final int index = this.index;
        final ExecuteVariant[] elements = this.elements;
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < index; i++) {
            map.putAll(elements[i].toMap());
        }
        return map;
    }

    private final void ensureCapacity(final int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = ((elements.length * 3) >> 1) + 1;
            newCapacity = newCapacity < minCapacity ? minCapacity : newCapacity;
            ExecuteVariant[] oldData = this.elements;
            this.elements = new ExecuteVariant[newCapacity];
            System.arraycopy(oldData, 0, this.elements, 0, size);
        }
    }
}
