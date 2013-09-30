package org.boilit.bsl.core;

import org.boilit.bsl.xio.IPrinter;

import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class ExecuteContext {
    public static final int CONTROL_GOON = 0xf0;
    public static final int CONTROL_NEXT = 0x01;
    public static final int CONTROL_BREAK = 0x03;

    private boolean loopGoon = true;
    private boolean blockGoon = true;
    private int control = CONTROL_GOON;

    private int size = 0;
    private int index = 0;
    private ExecuteVariant[] elements;

    private IPrinter printer;

    public ExecuteContext(final IPrinter printer) {
        this(null, printer);
    }

    public ExecuteContext(final Map<String, Object> model, final IPrinter printer) {
        elements = new ExecuteVariant[8];
        if (model != null) {
            this.occupy(model);
        }
        this.occupy();
        this.printer = printer;
    }

    public final boolean isLoopGoon() {
        return loopGoon;
    }

    public final boolean isBlockGoon() {
        return blockGoon;
    }

    public final int getControl() {
        return control;
    }

    public final int setControl(final int control) {
        switch (control) {
            case CONTROL_NEXT:
                loopGoon = true;
                blockGoon = false;
                break;
            case CONTROL_BREAK:
                loopGoon = false;
                blockGoon = false;
                break;
            default:
                loopGoon = true;
                blockGoon = true;
        }
        return this.control = control;
    }

    public final IPrinter getPrinter() {
        return printer;
    }

    public final ExecuteVariant occupy() {
        return this.occupy(null);
    }

    public final ExecuteVariant occupy(final Map<String, Object> model) {
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
        for (int i = size - 1; i >= 0; i--) {
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

    public final Object getVariable(final int index) {
        return elements[index >>> 16].get(index & 0xff);
    }

    public final Object setVariable(final int index, final Object value) {
        return elements[index >>> 16].set(index & 0xff, value);
    }

    private final void ensureCapacity(final int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = ((elements.length * 3) >> 1) + 1;
            newCapacity = newCapacity < minCapacity ? minCapacity : newCapacity;
            ExecuteVariant[] oldData = this.elements;
            this.elements = new ExecuteVariant[newCapacity];
            System.arraycopy(oldData, 0, this.elements, 0, oldData.length);
        }
    }
}
