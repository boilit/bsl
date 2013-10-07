package org.boilit.bsl.core;

import org.boilit.bsl.Context;
import org.boilit.bsl.Detection;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.exception.DetectException;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractOneselfOperator extends AbstractOperator {
    private final String label;
    private final boolean previous;
    private int labelMark = -1;

    public AbstractOneselfOperator(final int line, final int column, final String label,
                                   final boolean previous, final ITemplate template) {
        super(line, column, template);
        this.label = label;
        this.previous = previous;
    }

    @Override
    public final AbstractOneselfOperator detect() throws Exception {
        final Detection detection = this.getTemplate().getDetection();
        labelMark = detection.getVarIndex(label);
        if(labelMark == -1) {
            throw new DetectException(this, "Label[" + label + "] hasn't defined!");
        }
        return this;
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final boolean previous = this.previous;
        final int labelMark = this.labelMark;
        if (previous) {
            return context.setVariable(label, labelMark, this.executeFragment(context.getVariable(labelMark)));
        } else {
            final Object value = context.getVariable(labelMark);
            context.setVariable(label, labelMark, this.executeFragment(value));
            return value;
        }
    }

    protected abstract Object executeFragment(Object value) throws Exception;
}
