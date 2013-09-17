package org.boilit.bsl.core;

import org.boilit.bsl.core.exs.Value;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractBinaryOperator extends AbstractOperator {
    private AbstractExpression expression1;
    private AbstractExpression expression2;

    public AbstractBinaryOperator(final int line, final int column, final AbstractExpression expression1, final AbstractExpression expression2) {
        super(line, column);
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public final AbstractExpression optimize() throws Exception {
        if ((expression1 = expression1.optimize()) == null) {
            return null;
        }
        if ((expression2 = expression2.optimize()) == null) {
            return null;
        }
        if(expression1 instanceof Value && expression2 instanceof Value) {
            return this.optimizeConst();
        }
        return this;
    }

    protected abstract AbstractExpression optimizeConst() throws Exception;

    public final AbstractExpression getExpression1() {
        return expression1;
    }

    public final AbstractExpression getExpression2() {
        return expression2;
    }
}
