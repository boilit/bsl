package org.boilit.bsl.core;

import org.boilit.bsl.core.exs.Value;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractUnitaryOperator extends AbstractOperator {
    private AbstractExpression expression;

    public AbstractUnitaryOperator(final int line, final int column, final AbstractExpression expression) {
        super(line, column);
        this.expression = expression;
    }

    @Override
    public final AbstractExpression optimize() throws Exception {
        if ((expression = expression.optimize()) == null) {
            return null;
        }
        if(expression instanceof Value) {
            return this.optimizeConst();
        }
        return this;
    }

    protected abstract AbstractExpression optimizeConst() throws Exception;

    public final AbstractExpression getExpression() {
        return expression;
    }
}
