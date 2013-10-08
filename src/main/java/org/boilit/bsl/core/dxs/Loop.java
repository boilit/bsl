package org.boilit.bsl.core.dxs;

import org.boilit.bsl.Context;
import org.boilit.bsl.Detection;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.*;
import org.boilit.bsl.exception.DetectException;
import org.boilit.bsl.iterator.ObjectIterator;

import java.util.Iterator;

/**
 * @author Boilit
 * @see
 */
public final class Loop extends AbstractDirective {
    private final String label;
    private final String index;
    private AbstractExpression expression;
    private Block block;
    private int labelMark;
    private int indexMark;

    public Loop(final int line, final int column, final String label,
                final AbstractExpression expression, final Block block, final ITemplate template) {
        super(line, column, template);
        this.label = label;
        this.index = label.concat("_index");
        this.expression = expression;
        this.block = block;
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        int count = 0;
        final Iterator iterator = new ObjectIterator(expression.execute(context));
        final Block block = this.block;
        final String label = this.label;
        final String index = this.index;
        final int labelMark = this.labelMark;
        final int indexMark = this.indexMark;
        while (iterator.hasNext() && context.isLoopGoon()) {
            context.setControl(Context.CONTROL_GOON);
            context.setVariable(label, labelMark, iterator.next());
            context.setVariable(index, indexMark, count++);
            block.execute(context);
        }
        context.setControl(Context.CONTROL_GOON);
        return null;
    }

    @Override
    public final AbstractDirective optimize() throws Exception {
        if ((expression = expression.optimize()) == null) {
            return null;
        }
        if ((block = block.optimize()) == null) {
            return null;
        }
        return this;
    }

    @Override
    public final AbstractDirective detect() throws Exception {
        final Detection detection = this.getTemplate().getDetection();
        final String label = this.label;
        labelMark = detection.getVarIndex(label);
        if (labelMark != -1) {
            throw new DetectException(this, "Label[" + label + "] duplicated defined!");
        }
        detection.addVariable(label);
        labelMark = detection.getVarIndex(label);
        final String index = this.index;
        indexMark = detection.getVarIndex(index);
        if (indexMark != -1) {
            throw new DetectException(this, "Label[" + index + "] duplicated defined!");
        }
        detection.addVariable(index);
        indexMark = detection.getVarIndex(index);
        if (expression != null) {
            expression.detect();
        }
        if (block != null) {
            block.detect();
        }
        return this;
    }
}
