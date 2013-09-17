package org.boilit.bsl.core.sxs;

import org.boilit.bsl.core.AbstractStatement;
import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.core.IStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Root extends AbstractStatement {
    private IStatement[] statements;
    private List<IStatement> children;
    public Root(final int line, final int column) {
        super(line, column);
        this.children = new ArrayList<IStatement>();
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        final IStatement[] statements = this.statements;
        final int n = statements.length;
        for (int i = 0; i < n; i++) {
            statements[i].execute(context);
        }
        return null;
    }

    @Override
    public final Root optimize() throws Exception {
        statements = new IStatement[children.size()];
        children.toArray(statements);
        children.clear();
        children = null;
        return this;
    }

    public final Root add(IStatement statement) throws Exception {
        if ((statement = statement.optimize()) == null) {
            return this;
        }
        children.add(statement);
        return this;
    }
}
