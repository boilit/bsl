package org.boilit.bsl.core.dxs;

import org.boilit.bsl.Template;
import org.boilit.bsl.core.*;

import java.util.Map;

/**
 * @author Boilit
 * @see
 */
@SuppressWarnings("unchecked")
public final class Include extends AbstractDirective {
    private AbstractExpression expression1;
    private AbstractExpression expression2;
    private Template template;

    public Include(final int line, final int column,
                   final AbstractExpression expression1,
                   final AbstractExpression expression2,
                   final Template template) {
        super(line, column);
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.template = template;
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        final Template template = this.template;
        final Map parameters = this.expression2 == null? null :(Map) this.expression2.execute(context);
        final ExecuteContext ec = new ExecuteContext(parameters, context.getPrinter());
        final String location = Operation.toString(expression1.execute(context));
        final String name = this.relative(template.getResource().getName(), location).substring(1);
        template.getEngine().getTemplate(name).execute(ec);
        return null;
    }

    @Override
    public final Include optimize() throws Exception {
        if ((expression1 = expression1.optimize()) == null) {
            return null;
        }
        if (expression2 != null) {
            expression2 = expression2.optimize();
        }
        return this;
    }

    private final String relative(final String ref, final String aim) {
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
