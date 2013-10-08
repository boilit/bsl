package org.boilit.bsl.core.exo;

import org.boilit.acp.Proxy;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractOperator;
import org.boilit.bsl.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Nature extends AbstractOperator {
    public static final int FIELD = 0;
    public static final int METHOD = 1;
    protected final int kind;
    protected String label;
    protected Proxy proxy;
    protected boolean acting = false;
    protected AbstractExpression[] expressions;
    private List<AbstractExpression> children;

    public Nature(final int line, final int column, final int kind, final String label, final ITemplate template) {
        super(line, column, template);
        this.kind = kind;
        this.label = label;
        this.children = new ArrayList<AbstractExpression>();
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final AbstractExpression[] expressions = this.expressions;
        final Object[] values = new Object[expressions.length];
        for (int i = expressions.length - 1; i >= 0; i--) {
            values[i] = expressions[i].execute(context);
        }
        return values;
    }

    @Override
    public final Nature optimize() throws Exception {
        Collections.reverse(children);
        expressions = new AbstractExpression[children.size()];
        children.toArray(expressions);
        children.clear();
        children = null;
        return this;
    }

    @Override
    public final AbstractExpression detect() throws Exception {
        final AbstractExpression[] expressions = this.expressions;
        for (int i = expressions.length - 1; i >= 0; i--) {
            if (expressions[i] != null) {
                expressions[i].detect();
            }
        }
        return this;
    }

    public final Nature add(AbstractExpression expression) throws Exception {
        if ((expression = expression.optimize()) == null) {
            return this;
        }
        this.children.add(expression);
        return this;
    }

    public final Nature add(List<AbstractExpression> expressions) throws Exception {
        for (AbstractExpression expression : expressions) {
            add(expression);
        }
        return this;
    }
}
