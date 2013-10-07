package org.boilit.bsl.core.dxs;

import org.boilit.bsl.Context;
import org.boilit.bsl.Fragment;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.*;
import org.boilit.bsl.exception.ExecuteException;

import java.util.Map;

/**
 * @author Boilit
 * @see
 */
@SuppressWarnings("unchecked")
public final class Include extends AbstractDirective {
    private AbstractExpression expression1;
    private AbstractExpression expression2;

    public Include(final int line, final int column,
                   final AbstractExpression expression1,
                   final AbstractExpression expression2,
                   final ITemplate template) {
        super(line, column, template);
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final ITemplate template = this.getTemplate().getTemplate();
        final Map arguments = this.expression2 == null ? null : (Map) this.expression2.execute(context);
        final String location = Operation.toString(expression1.execute(context));
        final String name = this.relative(template.getResource().getName(), location).substring(1);
        final int separator = name.indexOf('#');
        if (separator == -1) {
            final ITemplate include = template.getEngine().getTemplate(name);
            if (include == null) {
                throw new ExecuteException(this, "Template[" + include.getResource().getName() + "] was not found!");
            }
            try {
                include.execute(new Context(include.getDetection(), context.getPrinter(), arguments));
            } catch (Exception e) {
                throw new ExecuteException(this, "Include Template[" + include.getResource().getName() + "] Error!", e);
            }
        } else {
            final ITemplate include = template.getEngine().getTemplate(name.substring(0, separator));
            if (include == null) {
                throw new ExecuteException(this, "Template[" + include.getResource().getName() + "] was not found!");
            }
            final String fragName = name.substring(separator + 1);
            final Fragment fragment = include.getFragments().get(fragName);
            if (fragment == null) {
                throw new ExecuteException(this, "Fragment[" + fragName + "] was not found in Template[" + include.getResource().getName() + "]!");
            }
            final Context fragContext = new Context(fragment.getDetection(), context.getPrinter(), arguments);
            final IStatement[] statements = fragment.getFragDefine().getStatements();
            final int n = statements.length;
            for (int i = 0; i < n; i++) {
                statements[i].execute(fragContext);
            }
        }
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

    @Override
    public final Include detect() throws Exception {
        if (expression1 != null) {
            expression1.detect();
        }
        if (expression2 != null) {
            expression2.detect();
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
