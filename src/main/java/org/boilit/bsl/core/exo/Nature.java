package org.boilit.bsl.core.exo;

import org.boilit.acp.ACP;
import org.boilit.acp.Proxy;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractOperator;
import org.boilit.bsl.core.ExecuteContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Nature extends AbstractOperator {
    protected final boolean field;
    protected final String label;
    protected Proxy proxy;
    protected boolean acting = false;
    protected AbstractExpression[] expressions;
    private List<AbstractExpression> children;

    public Nature(final int line, final int column, final boolean field, final String label) {
        super(line, column);
        this.field = field;
        this.label = label;
        this.children = new ArrayList<AbstractExpression>();
    }

    @Override
    public Object execute(final ExecuteContext context) throws Exception {
        final int n=expressions.length;
        final Object[] values = new Object[n];
        for (int i=0; i<n; i++) {
            values[i] = expressions[i].execute(context);
        }
        return values;
    }

    @Override
    public Nature optimize() {
        expressions = new AbstractExpression[children.size()];
        children.toArray(expressions);
        children.clear();
        children = null;
        return this;
    }

    public Nature add(AbstractExpression expression) throws Exception {
        if ((expression = expression.optimize()) == null) {
            return this;
        }
        this.children.add(expression);
        return this;
    }

    public Nature add(List<AbstractExpression> expressions) throws Exception {
        for (AbstractExpression expression : expressions) {
            add(expression);
        }
        return this;
    }
}
