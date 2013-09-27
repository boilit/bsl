package org.boilit.bsl.core.dxs;

import org.boilit.bsl.Template;
import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.core.exs.Hash;
import org.boilit.bsl.core.Operation;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
@SuppressWarnings("unchecked")
public final class Include extends AbstractDirective {
    private AbstractExpression expression;
    private Hash hash;
    private Template template;

    public Include(final int line, final int column, final AbstractExpression expression, final Hash hash, final Template template) {
        super(line, column);
        this.expression = expression;
        this.hash = hash;
        this.template = template;
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        final Template template = this.template;
        final String name = this.relative(
                template.getResource().getName(),
                Operation.toString(expression.execute(context))
        ).substring(1);
        final ExecuteContext ec = context.cloneExecuteContext();
        if (this.hash != null) {
            ec.occupy((Map) this.hash.execute(context));
        }
        template.getEngine().getTemplate(name).execute(ec);
        return null;
    }

    @Override
    public final Include optimize() throws Exception {
        if ((expression = expression.optimize()) == null) {
            return null;
        }
        if (hash != null) {
            hash = hash.optimize();
        }
        return this;
    }

    private final String relative(String ref, String aim) {
        if (aim.charAt(0) == '/') {
            return aim;
        }
        String value = ref;
        if (value.charAt(0) != '/') {
            value = '/' + value;
        }
        int index = value.lastIndexOf('/');
        value = index == -1 ? value : value.substring(0, index + 1);
        value = value.concat(aim);
        while ((index = value.indexOf("/./")) >= 0) {
            value = value.substring(0, index) + value.substring(index + 2);
        }
        while ((index = value.indexOf("/../")) >= 0) {
            if (index == 0) {
                value = value.substring(3);
            } else {
                value = value.substring(0, value.lastIndexOf('/', index - 1)) + value.substring(index + 3);
            }
        }
        while ((index = value.indexOf("/../")) >= 0) {
            if (index == 0) {
                value = value.substring(3);
            } else {
                value = value.substring(0, value.lastIndexOf('/', index - 1)) + value.substring(index + 3);
            }
        }
        return value;
    }
}
