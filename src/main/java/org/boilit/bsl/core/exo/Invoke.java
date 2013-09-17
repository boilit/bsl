package org.boilit.bsl.core.exo;

import org.boilit.acp.ACP;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractOperator;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.exception.ExecuteException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
@SuppressWarnings("unchecked")
public final class Invoke extends AbstractOperator {
    protected AbstractExpression expression;
    private Nature[] natures;
    private List<Nature> children;

    public Invoke(final int line, final int column, final AbstractExpression expression) {
        super(line, column);
        this.expression = expression;
        this.children = new ArrayList<Nature>();
    }

    @Override
    public Object execute(final ExecuteContext context) throws Exception {
        Object value = expression.execute(context);
        final Nature[] natures = this.natures;
        final int n = natures.length;
        for (int i = 0; i < n; i++) {
            if (value == null) {
                return null;
            }
            if (!natures[i].acting) {
                if (natures[i].field) {
                    natures[i].proxy = ACP.proxyField(value.getClass(), natures[i].label);
                } else {
                    Object[] parameters = (Object[]) natures[i].execute(context);
                    natures[i].proxy = ACP.proxyMethod(value.getClass(), natures[i].label, parameters);
                }
                natures[i].acting = true;
            }
            if (natures[i].proxy == null) {
                throw new ExecuteException(natures[i], "Can't proxy[" + natures[i].label + "]!");
            }
            if (natures[i].field) {
                value = natures[i].proxy.invoke(value, null);
            } else {
                value = natures[i].proxy.invoke(value, (Object[])natures[i].execute(context));
            }
        }
        return value;
    }

    @Override
    public AbstractExpression optimize() throws Exception {
        if ((expression = expression.optimize()) == null) {
            return null;
        }
        natures = new Nature[children.size()];
        children.toArray(natures);
        children.clear();
        children = null;
        return this;
    }

    public Invoke add(Nature expression) throws Exception {
        if ((expression = expression.optimize()) == null) {
            return this;
        }
        this.children.add(expression);
        return this;
    }
}
