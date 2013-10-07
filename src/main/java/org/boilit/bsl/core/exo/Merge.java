package org.boilit.bsl.core.exo;

import org.boilit.bsl.Context;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Merge extends AbstractOperator {
    private AbstractExpression[] expressions;
    private List<AbstractExpression> children;

    public Merge(final int line, final int column, final ITemplate template) {
        super(line, column, template);
        this.children = new ArrayList<AbstractExpression>();
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        Object value;
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0, n = expressions.length; i < n; i++) {
            value = expressions[i].execute(context);
            buffer.append(Operation.toString(value));
        }
        return buffer.toString();
    }

    @Override
    public final AbstractExpression optimize() throws Exception {
        expressions = new AbstractExpression[children.size()];
        children.toArray(expressions);
        children.clear();
        children = null;
        return this;
    }

    @Override
    public final AbstractExpression detect() throws Exception {
        final AbstractExpression[] expressions = this.expressions;
        for(int i=0, n=expressions.length; i<n; i++) {
            if(expressions[i] != null) {
                expressions[i].detect();
            }
        }
        return this;
    }

    public final Merge add(AbstractExpression expression) throws Exception {
        if ((expression = expression.optimize()) == null) {
            return this;
        }
        this.children.add(expression);
        return this;
    }
}
