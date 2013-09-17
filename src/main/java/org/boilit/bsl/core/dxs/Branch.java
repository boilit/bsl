package org.boilit.bsl.core.dxs;

import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.core.IStatement;
import org.boilit.bsl.core.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Branch extends AbstractDirective {
    private IStatement[][] statements;
    private List<IStatement[]> children;

    public Branch(final int line, final int column) {
        super(line, column);
        this.children = new ArrayList<IStatement[]>();
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        final IStatement[][] statements = this.statements;
        final int n = statements.length - 1;
        for (int i = 0; i < n; i++) {
            if (Operation.toBool(statements[i][0].execute(context))) {
                context.occupy();
                statements[i][1].execute(context);
                context.revert();
                return null;
            }
        }
        // else or last else if
        if (statements[n][0] == null || Operation.toBool(statements[n][0].execute(context))) {
            context.occupy();
            statements[n][1].execute(context);
            context.revert();
        }
        return null;
    }

    @Override
    public final AbstractDirective optimize() {
        if (children.size() == 0) {
            return null;
        } else if (children.size() == 1) {
            // if not exist but else is first
            if(children.get(0)[0] == null) {
                return null;
            }
        }
        statements = new IStatement[children.size()][2];
        children.toArray(statements);
        children.clear();
        children = null;
        return this;
    }

    public final Branch add(AbstractExpression expression, Block block) throws Exception {
        if ((expression = expression.optimize()) == null) {
            return this;
        }
        if ((block = block.optimize()) == null) {
            return this;
        }
        children.add(new IStatement[]{expression, block});
        return this;
    }

    public final Branch addElse(Block block) throws Exception {
        if ((block = block.optimize()) == null) {
            return this;
        }
        children.add(new IStatement[]{null, block});
        return this;
    }
}
