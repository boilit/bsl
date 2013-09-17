package org.boilit.bsl.core.exs;

import org.boilit.bsl.core.AbstractStructure;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.exception.ExecuteException;

/**
 * @author Boilit
 * @see
 */
public final class Label extends AbstractStructure {
    private final String label;
    private int labelIndex = -1;

    public Label(final int line, final int column, final String label) {
        super(line, column);
        this.label = label;
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
        return context.getVariable(labelIndex);
    }
}
