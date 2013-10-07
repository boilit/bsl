package org.boilit.bsl.core.exo;

import org.boilit.bsl.Context;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.*;

/**
 * @author Boilit
 * @see
 */
public final class QuestionT extends AbstractOperator {
    protected AbstractExpression expression1;
    protected AbstractExpression expression2;

    public QuestionT(final int line, final int column,
                     final AbstractExpression expression1,
                     final AbstractExpression expression2,
                     final ITemplate template) {
        super(line, column, template);
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final Object value = expression1.execute(context);
        return Operation.toBool(value) ? expression2.execute(context) : value;
    }

    @Override
    public final AbstractExpression optimize() throws Exception {
        if ((expression1 = expression1.optimize()) == null) {
            return null;
        }
        if (expression2 == null || (expression2 = expression2.optimize()) == null) {
            return null;
        }
        return this;
    }

    @Override
    public final AbstractExpression detect() throws Exception {
        if(expression1 != null) {
            expression1.detect();
        }
        if(expression2 != null) {
            expression2.detect();
        }
        return this;
    }
}
