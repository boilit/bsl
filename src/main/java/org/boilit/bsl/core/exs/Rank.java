package org.boilit.bsl.core.exs;

import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractStructure;
import org.boilit.bsl.core.ExecuteContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Rank extends AbstractStructure {
    private AbstractExpression[] expressions;
    private List<AbstractExpression> children;

    public Rank(final int line, final int column) {
        super(line, column);
        this.children = new ArrayList<AbstractExpression>();
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        final AbstractExpression[] expressions = this.expressions;
        final int n = expressions.length;
        final List<Object> result = new ArrayList<Object>(n);
        for (int i = 0; i < n; i++) {
            result.add(expressions[i].execute(context));
        }
        return result;
    }

    @Override
    public final AbstractExpression optimize() {
        expressions = new AbstractExpression[children.size()];
        children.toArray(expressions);
        children.clear();
        children = null;
        return this;
    }

    public final Rank add(AbstractExpression expression) throws Exception {
        if ((expression = expression.optimize()) == null) {
            return this;
        }
        this.children.add(expression);
        return this;
    }
}
