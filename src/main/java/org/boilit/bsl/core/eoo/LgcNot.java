package org.boilit.bsl.core.eoo;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractUnitaryOperator;
import org.boilit.bsl.Context;
import org.boilit.bsl.core.exo.Value;
import org.boilit.bsl.core.Operation;

/**
 * @author Boilit
 * @see
 */
public final class LgcNot extends AbstractUnitaryOperator {

    public LgcNot(final int line, final int column, final AbstractExpression expression, final ITemplate template) {
        super(line, column, expression, template);
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        return Operation.doLgcNot(this.getExpression().execute(context));
    }

    @Override
    public final AbstractExpression optimizeConst() throws Exception {
        return new Value(
                this.getLine(),
                this.getColumn(),
                Operation.doLgcNot(this.getExpression().execute(null)),
                this.getTemplate()
        );
    }
}
