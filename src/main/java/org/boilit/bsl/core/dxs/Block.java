package org.boilit.bsl.core.dxs;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.Detection;
import org.boilit.bsl.Context;
import org.boilit.bsl.core.IStatement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Block extends AbstractDirective {
    private IStatement[] statements;
    private List<IStatement> children;

    public Block(final int line, final int column, final ITemplate template) {
        super(line, column, template);
        this.children = new ArrayList<IStatement>();
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final IStatement[] statements = this.statements;
        for (int i = statements.length - 1; i >= 0 && context.isBlockGoon(); i--) {
            statements[i].execute(context);
        }
        return null;
    }

    @Override
    public final Block optimize() throws Exception {
        if (children.size() == 0) {
            return null;
        }
        Collections.reverse(children);
        statements = new IStatement[children.size()];
        children.toArray(statements);
        children.clear();
        children = null;
        return this;
    }

    @Override
    public final Block detect() throws Exception {
        final Detection detection = this.getTemplate().getDetection();
        detection.occupy();
        final IStatement[] statements = this.statements;
        for (int i = statements.length - 1; i >= 0; i--) {
            if (statements[i] != null) {
                statements[i].detect();
            }
        }
        detection.revert();
        return this;
    }

    public final Block add(IStatement statement) throws Exception {
        if ((statement = statement.optimize()) == null) {
            return this;
        }
        this.children.add(statement);
        return this;
    }
}
