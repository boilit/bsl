package org.boilit.bsl.core.dxs;

import org.boilit.bsl.Context;
import org.boilit.bsl.Detection;
import org.boilit.bsl.Fragment;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractStatement;
import org.boilit.bsl.core.IStatement;
import org.boilit.bsl.exception.ExecuteException;

import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class FragExec extends AbstractStatement {
    private final String label;
    private AbstractExpression expression;

    public FragExec(final int line, final int column, final String label,
                    final AbstractExpression expression, final ITemplate template) {
        super(line, column, template);
        this.label = label;
        this.expression = expression;
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final Fragment fragment = this.getTemplate().getTemplate().getFragments().get(label);
        if (fragment == null) {
            throw new ExecuteException(this, "Fragment[" + label + "] was not existed!");
        }
        final Map arguments = expression == null ? null : (Map) expression.execute(context);
        final Context fragContext = new Context(fragment.getDetection(), context.getPrinter(), arguments);
        final IStatement[] statements = fragment.getFragDefine().getStatements();
        for (int i = statements.length - 1; i >= 0; i--) {
            statements[i].execute(fragContext);
        }
        return null;
    }

    @Override
    public final FragExec optimize() throws Exception {
        if (expression != null) {
            expression = expression.optimize();
        }
        return this;
    }

    @Override
    public final FragExec detect() throws Exception {
        if (expression != null) {
            expression.detect();
        }
        return this;
    }

    public final String getLabel() {
        return label;
    }
}
