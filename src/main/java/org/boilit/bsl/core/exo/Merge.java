package org.boilit.bsl.core.exo;

import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractOperator;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.core.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Merge extends AbstractOperator {
    private AbstractExpression[] expressions;
    private List<AbstractExpression> children;

    public Merge(final int line, final int column) {
        super(line, column);
        this.children = new ArrayList<AbstractExpression>();
    }

    @Override
    public Object execute(final ExecuteContext context) throws Exception {
        Object value;
        final StringBuilder builder = new StringBuilder();
        for (int i = 0, n = expressions.length; i < n; i++) {
            value = expressions[i].execute(context);
            builder.append(Operation.toString(value));
        }
        return builder.toString();
    }

    @Override
    public AbstractExpression optimize() throws Exception {
        expressions = new AbstractExpression[children.size()];
        children.toArray(expressions);
        children.clear();
        children = null;
        return this;
    }

    public Merge add(AbstractExpression expression) throws Exception {
        if ((expression = expression.optimize()) == null) {
            return this;
        }
        this.children.add(expression);
        return this;
    }
}
