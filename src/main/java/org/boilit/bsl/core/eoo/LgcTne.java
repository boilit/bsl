package org.boilit.bsl.core.eoo;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractBinaryOperator;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.Context;
import org.boilit.bsl.core.exo.Value;
import org.boilit.bsl.core.Operation;

/**
 * @author Boilit
 * @see
 */
public final class LgcTne extends AbstractBinaryOperator {

    public LgcTne(final int line, final int column,
                  final AbstractExpression expression1,
                  final AbstractExpression expression2,
                  final ITemplate template) {
        super(line, column, expression1, expression2, template);
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        return Operation.doLgcTne(
                this.getExpression1().execute(context),
                this.getExpression2().execute(context)
        );
    }

    @Override
    protected final AbstractExpression optimizeConst() throws Exception {
        return new Value(
                this.getLine(),
                this.getColumn(),
                Operation.doLgcTne(
                        this.getExpression1().execute(null),
                        this.getExpression2().execute(null)
                ),
                this.getTemplate()
        );
    }
}
