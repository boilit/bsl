package org.boilit.bsl.core.exo;

import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractOperator;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.core.Operation;

/**
 * @author Boilit
 * @see
 */
public final class Question extends AbstractOperator {
    protected AbstractExpression expression1;
    protected AbstractExpression expression2;
    protected AbstractExpression expression3;

    public Question(final int line, final int column, final AbstractExpression exp1, final AbstractExpression exp2, final AbstractExpression exp3) {
        super(line, column);
        this.expression1 = exp1;
        this.expression2 = exp2;
        this.expression3 = exp3;
    }

    @Override
    public Object execute(final ExecuteContext context) throws Exception {
        return Operation.toBool(expression1.execute(context)) ? expression2.execute(context) : expression3.execute(context);
    }

    @Override
    public AbstractExpression optimize() throws Exception {
        if ((expression1 = expression1.optimize()) == null) {
            return null;
        }
        if (expression2 == null || (expression2 = expression2.optimize()) == null) {
            return null;
        }
        if (expression3 == null || (expression3 = expression3.optimize()) == null) {
            return null;
        }
        return this;
    }
}
