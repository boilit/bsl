package org.boilit.bsl.core.eao;

import org.boilit.bsl.Context;
import org.boilit.bsl.Detection;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.*;
import org.boilit.bsl.exception.DetectException;

/**
 * @author Boilit
 * @see
 */
public final class NormalAssign extends AbstractOperator {
    private final String label;
    private AbstractExpression expression;
    private int labelMark;

    public NormalAssign(final int line, final int column, final String label,
                        final AbstractExpression expression, final ITemplate template) {
        super(line, column, template);
        this.label = label;
        this.expression = expression;
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        return context.setVariable(label, labelMark, this.getExpression().execute(context));
    }

    @Override
    public final NormalAssign optimize() throws Exception {
        if ((expression = expression.optimize()) == null) {
            return null;
        }
        return this;
    }

    @Override
    public final NormalAssign detect() throws Exception {
        final Detection detection = this.getTemplate().getDetection();
        labelMark = detection.getVarIndex(label);
        if(labelMark == -1) {
            throw new DetectException(this, "Label["+ label +"] hasn't defined!");
        }
        if(expression != null) {
            expression.detect();
        }
        return this;
    }

    public final String getLabel() {
        return label;
    }

    public final AbstractExpression getExpression() {
        return expression;
    }
}
