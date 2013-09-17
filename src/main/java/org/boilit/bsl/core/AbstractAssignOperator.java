package org.boilit.bsl.core;

import org.boilit.bsl.exception.ExecuteException;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractAssignOperator extends AbstractOperator {
    private final String label;
    private AbstractExpression expression;
    private int labelIndex = -1;

    public AbstractAssignOperator(final int line, final int column, final String label, final AbstractExpression expression) {
        super(line, column);
        this.label = label;
        this.expression = expression;
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        int labelIndex = this.labelIndex;
        if(labelIndex == -1){
            labelIndex = context.getVariableIndex(label);
            if (labelIndex == -1) {
                throw new ExecuteException(this, "Label[" + label + "] hasn't defined!");
            }
            this.labelIndex = labelIndex;
        }
        return context.setVariable(labelIndex, this.executeFragment(context.getVariable(labelIndex), expression.execute(context)));
    }

    protected abstract Object executeFragment(final Object value1, final Object value2) throws Exception;

    @Override
    public final AbstractAssignOperator optimize() throws Exception {
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
