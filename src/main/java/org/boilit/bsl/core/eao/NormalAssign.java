package org.boilit.bsl.core.eao;

import org.boilit.bsl.core.AbstractAssignOperator;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractOperator;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.exception.ExecuteException;

/**
 * @author Boilit
 * @see
 */
public final class NormalAssign extends AbstractOperator {
    private final String label;
    private AbstractExpression expression;
    private int labelIndex = -1;

    public NormalAssign(final int line, final int column, final String label, final AbstractExpression expression) {
        super(line, column);
        this.label = label;
        this.expression = expression;
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        int labelIndex = this.labelIndex;
        if (labelIndex == -1) {
            labelIndex = context.getVariableIndex(this.getLabel());
            if (labelIndex == -1) {
                throw new ExecuteException(this, "Label[" + this.getLabel() + "] hasn't defined!");
            }
            this.labelIndex = labelIndex;
        }
        return context.setVariable(labelIndex, this.getExpression().execute(context));
    }

    @Override
    public final NormalAssign optimize() throws Exception {
        if ((expression = expression.optimize()) == null) {
            return null;
        }
        return this;
    }

    public final String getLabel() {
        return label;
    }

    public final AbstractExpression getExpression() {
        return expression;
    }
}
