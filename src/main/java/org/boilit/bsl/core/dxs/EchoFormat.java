package org.boilit.bsl.core.dxs;

import org.boilit.bsl.Template;
import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.exception.ExecuteException;
import org.boilit.bsl.formatter.FormatterManager;

/**
 * @author Boilit
 * @see
 */
public final class EchoFormat extends AbstractDirective {
    private AbstractExpression expression;
    private final String format;
    private int formatterIndex = -1;
    private final FormatterManager formatterManager;

    public EchoFormat(final int line, final int column, final AbstractExpression expression, final String format, final Template template) {
        super(line, column);
        this.expression = expression;
        this.format = format;
        this.formatterManager = template.getFormatterManager();
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        final Object value = expression.execute(context);
        if (value == null) {
            return null;
        }
        int formatterIndex = this.formatterIndex;
        if (formatterIndex == -1) {
            formatterIndex = formatterManager.getIndex(value.getClass(), format);
            if (formatterIndex == -1) {
                throw new ExecuteException(this, "No formatter was found for[" + value.getClass().getName() + ", " + format + "]");
            }
            this.formatterIndex = formatterIndex;
        }
        context.getPrinter().print(formatterManager.get(formatterIndex).format(value));
        return null;
    }

    @Override
    public final AbstractDirective optimize() throws Exception {
        if ((expression = expression.optimize()) == null) {
            return null;
        }
        return this;
    }
}
