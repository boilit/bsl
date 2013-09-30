package org.boilit.bsl.core.ebo;

import org.boilit.bsl.core.AbstractBinaryOperator;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.core.exs.Value;
import org.boilit.bsl.core.Operation;

/**
 * @author Boilit
 * @see
 */
public final class BitRm extends AbstractBinaryOperator {

    public BitRm(final int line, final int column, final AbstractExpression expression1, final AbstractExpression expression2) {
        super(line, column, expression1, expression2);
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        return Operation.doBitRm(this,
                this.getExpression1().execute(context),
                this.getExpression2().execute(context)
        );
    }

    @Override
    protected final AbstractExpression optimizeConst() throws Exception {
        return new Value(
                this.getLine(),
                this.getColumn(),
                Operation.doBitRm(this,
                        this.getExpression1().execute(null),
                        this.getExpression2().execute(null)
                ));
    }
}
