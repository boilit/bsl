package org.boilit.bsl.core.exo;

import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractOperator;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.core.Operation;

/**
 * @author Boilit
 * @see
 */
public final class QuestionT extends AbstractOperator {
    protected AbstractExpression expression1;
    protected AbstractExpression expression2;

    public QuestionT(final int line, final int column, final AbstractExpression exp1, final AbstractExpression exp2) {
        super(line, column);
        this.expression1 = exp1;
        this.expression2 = exp2;
    }

    @Override
    public Object execute(final ExecuteContext context) throws Exception {
        final Object value = expression1.execute(context);
        return Operation.toBool(value) ? expression2.execute(context) : value;
    }

    @Override
    public AbstractExpression optimize() throws Exception {
        if ((expression1 = expression1.optimize()) == null) {
            return null;
        }
        if (expression2 == null || (expression2 = expression2.optimize()) == null) {
            return null;
        }
        return this;
    }
}
