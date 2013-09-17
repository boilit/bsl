package org.boilit.bsl.core.exs;

import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractStructure;
import org.boilit.bsl.core.ExecuteContext;

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

    public Hash(final int line, final int column) {
        super(line, column);
        this.children = new ArrayList<AbstractExpression[]>();
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        final Map<Object, Object> result = new HashMap<Object, Object>(expressions.length, 0.75f);
        for (int i = 0, n = expressions.length; i < n; i++) {
            result.put(expressions[i][0].execute(context), expressions[i][1].execute(context));
        }
        return result;
    }

    @Override
    public final Hash optimize() {
        expressions = new AbstractExpression[children.size()][2];
        children.toArray(expressions);
        children.clear();
        children = null;
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
