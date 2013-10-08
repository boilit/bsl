package org.boilit.bsl.core.exo;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractStructure;
import org.boilit.bsl.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Rank extends AbstractStructure {
    private AbstractExpression[] expressions;
    private List<AbstractExpression> children;

    public Rank(final int line, final int column, final ITemplate template) {
        super(line, column, template);
        this.children = new ArrayList<AbstractExpression>();
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final AbstractExpression[] expressions = this.expressions;
        final List<Object> result = new ArrayList<Object>(expressions.length);
        for (int i = expressions.length - 1; i >= 0; i--) {
            result.add(expressions[i].execute(context));
        }
        return result;
    }

    @Override
    public final AbstractExpression optimize() throws Exception {
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

    public final Rank add(AbstractExpression expression) throws Exception {
        if ((expression = expression.optimize()) == null) {
            return this;
        }
        this.children.add(expression);
        return this;
    }
}
