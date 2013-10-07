package org.boilit.bsl.core.exo;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractStructure;
import org.boilit.bsl.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class Hash extends AbstractStructure {
    private AbstractExpression[][] expressions;
    private List<AbstractExpression[]> children;

    public Hash(final int line, final int column, final ITemplate template) {
        super(line, column, template);
        this.children = new ArrayList<AbstractExpression[]>();
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final Map<Object, Object> result = new HashMap<Object, Object>(expressions.length, 0.75f);
        for (int i = 0, n = expressions.length; i < n; i++) {
            result.put(expressions[i][0].execute(context), expressions[i][1].execute(context));
        }
        return result;
    }

    @Override
    public final Hash optimize() throws Exception {
        expressions = new AbstractExpression[children.size()][2];
        children.toArray(expressions);
        children.clear();
        children = null;
        return this;
    }

    @Override
    public final AbstractExpression detect() throws Exception {
        final AbstractExpression[][] expressions = this.expressions;
        for (int i = 0, n = expressions.length; i < n; i++) {
            if (expressions[i][0] != null) {
                expressions[i][0].detect();
            }
            if (expressions[i][1] != null) {
                expressions[i][1].detect();
            }
        }
        return this;
    }

    public final Hash add(AbstractExpression key, AbstractExpression value) throws Exception {
        if ((key = key.optimize()) == null || (value = value.optimize()) == null) {
            return this;
        }
        this.children.add(new AbstractExpression[]{key, value});
        return this;
    }
}
