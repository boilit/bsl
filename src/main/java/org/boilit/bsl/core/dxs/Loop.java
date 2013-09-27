package org.boilit.bsl.core.dxs;

import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.exception.ExecuteException;
import org.boilit.bsl.iterator.ObjectIterator;

import java.util.Iterator;

/**
 * @author Boilit
 * @see
 */
public final class Loop extends AbstractDirective {
    private final String label;
    private AbstractExpression expression;
    private Block block;
    private int labelIndex = -1;

    public Loop(final int line, final int column, final String label, final AbstractExpression expression, final Block block) {
        super(line, column);
        this.label = label;
        this.expression = expression;
        this.block = block;
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        final String label = this.label;
        final String index = label.concat("_index");
        if (labelIndex == -1) {
            labelIndex = context.getVariableIndex(label);
            if (labelIndex != -1) {
                throw new ExecuteException(this, "Label[" + label + "] duplicated defined!");
            }
            labelIndex = context.getVariableIndex(index);
            if (labelIndex != -1) {
                throw new ExecuteException(this, "Label[" + index + "] duplicated defined!");
            }
            labelIndex = -2;
        }
        int count = 0;
        final Iterator iterator = new ObjectIterator(expression.execute(context));
        final Block block = this.block;
        while (iterator.hasNext() && context.isLoopGoon()) {
            context.occupy();
            context.addVariable(label, iterator.next());
            context.addVariable(index, count++);
            block.execute(context);
            context.revert();
        }
        context.setControl(ExecuteContext.CONTROL_GOON);
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
}
