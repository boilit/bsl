package org.boilit.bsl.core;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.exo.Value;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractUnitaryOperator extends AbstractOperator {
    private AbstractExpression expression;

    public AbstractUnitaryOperator(final int line, final int column,
                                   final AbstractExpression expression,
                                   final ITemplate template) {
        super(line, column, template);
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

    @Override
    public final AbstractExpression detect() throws Exception {
        if(expression != null) {
            expression.detect();
        }
        return this;
    }

    protected abstract AbstractExpression optimizeConst() throws Exception;

    public final AbstractExpression getExpression() {
        return expression;
    }
}
