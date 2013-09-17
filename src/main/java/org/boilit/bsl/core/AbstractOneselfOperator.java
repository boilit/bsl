package org.boilit.bsl.core;

import org.boilit.bsl.exception.ExecuteException;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractOneselfOperator extends AbstractOperator {
    private final String label;
    private final boolean previous;
    private int labelIndex = -1;

    public AbstractOneselfOperator(final int line, final int column, final String label, final boolean previous) {
        super(line, column);
        this.label = label;
        this.previous = previous;
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        int labelIndex = this.labelIndex;
        if (labelIndex == -1) {
            labelIndex = context.getVariableIndex(label);
            if (labelIndex == -1) {
                throw new ExecuteException(this, "Label[" + label + "] hasn't defined!");
            }
            this.labelIndex = labelIndex;
        }
        final boolean previous = this.previous;
        if (previous) {
            return context.setVariable(labelIndex, this.executeFragment(context.getVariable(labelIndex)));
        } else {
            final Object value = context.getVariable(labelIndex);
            context.setVariable(labelIndex, this.executeFragment(value));
            return value;
        }
    }

    protected abstract Object executeFragment(Object value) throws Exception;
}
