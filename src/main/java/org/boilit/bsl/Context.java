package org.boilit.bsl;

import org.boilit.bsl.core.ArrayWrapper;
import org.boilit.bsl.exception.ScriptException;
import org.boilit.bsl.xio.IPrinter;

import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class Context {
    public static final int CONTROL_GOON = 0xf0;
    public static final int CONTROL_NEXT = 0x01;
    public static final int CONTROL_BREAK = 0x03;

    private boolean loopGoon = true;
    private boolean blockGoon = true;
    private int control = CONTROL_GOON;

    private final String[] labels;
    private final Object[] elements;

    private final IPrinter printer;

    public Context(final Detection detection, final IPrinter printer) throws Exception {
        this(detection, printer, null);
    }

    public Context(final Detection detection, final IPrinter printer, final Map<String, Object> model) throws Exception {
        final int argSize = detection.getArgSize();
        this.printer = printer;
        this.labels = new String[detection.getMaxSize()];
        this.elements = new Object[detection.getMaxSize()];
        final int[] argIndexes = detection.getArgIndexes();
        final String[] arguments = detection.getArguments();
        for (int i = argSize - 1; i >= 0; i--) {
            this.setVariable(arguments[i], argIndexes[i], model == null ? null : arrayDetect(model.get(arguments[i])));
        }
    }

    private final Object arrayDetect(final Object value) {
        if (value == null) {
            return null;
        } else if (value.getClass().isArray()) {
            return new ArrayWrapper((Object[]) value);
        } else {
            return value;
        }
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

    public final void setControl(final int control) {
        this.control = control;
        switch (control) {
            case CONTROL_NEXT: {
                loopGoon = true;
                blockGoon = false;
                break;
            }
            case CONTROL_BREAK: {
                loopGoon = false;
                blockGoon = false;
                break;
            }
            case CONTROL_GOON: {
                loopGoon = true;
                blockGoon = true;
                break;
            }
        }
    }

    public final IPrinter getPrinter() {
        return printer;
    }

    public final Object getVariable(final int index) {
        return elements[index];
    }

    public final Object setVariable(final String label, final int index, final Object value) {
        labels[index] = label;
        return elements[index] = value;
    }

    public final void destroy() {
        final Object[] labels = this.labels;
        for (int i = labels.length - 1; i >= 0; i--) {
            labels[i] = null;
        }
        final Object[] elements = this.elements;
        for (int i = elements.length - 1; i >= 0; i--) {
            elements[i] = null;
        }
    }
}
