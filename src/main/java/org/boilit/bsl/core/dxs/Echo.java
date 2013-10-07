package org.boilit.bsl.core.dxs;

import org.boilit.bsl.Context;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.*;

/**
 * @author Boilit
 * @see
 */
public final class Echo extends AbstractDirective {
    private AbstractExpression expression;

    public Echo(final int line, final int column, final AbstractExpression expression, final ITemplate template) {
        super(line, column, template);
        this.expression = expression;
    }

    @Override
    public final Object execute(final Context context) throws Exception {
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

    @Override
    public final AbstractStatement detect() throws Exception {
        if(expression != null) {
            expression.detect();
        }
        return this;
    }
}
