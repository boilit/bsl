package org.boilit.bsl.core.dxs;

import org.boilit.bsl.Context;
import org.boilit.bsl.Detection;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.*;
import org.boilit.bsl.exception.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Branch extends AbstractDirective {
    private IStatement[][] statements;
    private List<IStatement[]> children;

    public Branch(final int line, final int column, final ITemplate template) {
        super(line, column, template);
        this.children = new ArrayList<IStatement[]>();
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final IStatement[][] statements = this.statements;
        for (int i = statements.length - 1; i > 0; i--) {
            if (Operation.toBool(statements[i][0].execute(context))) {
                statements[i][1].execute(context);
                return null;
            }
        }
        // else or last else if
        if (statements[0][0] == null || Operation.toBool(statements[0][0].execute(context))) {
            statements[0][1].execute(context);
        }
        return null;
    }

    @Override
    public final AbstractDirective optimize() {
        if (children.size() == 0) {
            return null;
        } else if (children.size() == 1) {
            // if not exist but else is first
            if (children.get(0)[0] == null) {
                return null;
            }
        }
        Collections.reverse(children);
        statements = new IStatement[children.size()][2];
        children.toArray(statements);
        children.clear();
        children = null;
        return this;
    }

    @Override
    public final AbstractDirective detect() throws Exception {
        final Detection detection = this.getTemplate().getDetection();
        final IStatement[][] statements = this.statements;
        for (int i = statements.length - 1; i >= 0; i--) {
            if (statements[i][0] != null) {
                statements[i][0].detect();
            }
            if (statements[i][1] != null) {
                detection.occupy();
                statements[i][1].detect();
                detection.revert();
            }
        }
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
        if (children.size() == 0) {
            throw new ParseException(this, "Syntax Error, no if, else if existed!");
        }
        if ((block = block.optimize()) == null) {
            return this;
        }
        children.add(new IStatement[]{null, block});
        return this;
    }
}
