package org.boilit.bsl.core.dxs;

import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.ExecuteContext;

/**
 * @author Boilit
 * @see
 */
public final class Echo extends AbstractDirective {
    private AbstractExpression expression;

    public Echo(final int line, final int column, final AbstractExpression expression) {
        super(line, column);
        this.expression = expression;
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        context.getPrinter().print(expression.execute(context));
        return null;
    }

    @Override
    public final AbstractDirective optimize() throws Exception {
        if ((expression = expression.optimize()) == null) {
            return null;
        }
        return this;
    }
}
