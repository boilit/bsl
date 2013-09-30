package org.boilit.bsl.core.euo;

import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractUnitaryOperator;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.core.exs.Value;
import org.boilit.bsl.core.Operation;

/**
 * @author Boilit
 * @see
 */
public final class NumNgt extends AbstractUnitaryOperator {

    public NumNgt(final int line, final int column, final AbstractExpression expression) {
        super(line, column, expression);
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        return Operation.doNumNgt(this, this.getExpression().execute(context));
    }

    @Override
    public final AbstractExpression optimizeConst() throws Exception {
        return new Value(this.getLine(), this.getColumn(), Operation.doNumNgt(this, this.getExpression().execute(null)));
    }
}
