package org.boilit.bsl.core.exo;

import org.boilit.bsl.Context;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.*;

/**
 * @author Boilit
 * @see
 */
public final class Question extends AbstractOperator {
    protected AbstractExpression expression1;
    protected AbstractExpression expression2;
    protected AbstractExpression expression3;

    public Question(final int line, final int column,
                    final AbstractExpression expression1,
                    final AbstractExpression expression2,
                    final AbstractExpression expression3,
                    final ITemplate template) {
        super(line, column, template);
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        return Operation.toBool(expression1.execute(context)) ? expression2.execute(context) : expression3.execute(context);
    }

    @Override
    public final AbstractExpression optimize() throws Exception {
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

    @Override
    public final AbstractExpression detect() throws Exception {
        if(expression1 != null) {
            expression1.detect();
        }
        if(expression2 != null) {
            expression2.detect();
        }
        if(expression3 != null) {
            expression3.detect();
        }
        return this;
    }
}
