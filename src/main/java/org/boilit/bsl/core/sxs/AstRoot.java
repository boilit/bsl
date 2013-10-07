package org.boilit.bsl.core.sxs;

import org.boilit.bsl.Context;
import org.boilit.bsl.Detection;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractStatement;
import org.boilit.bsl.core.IStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class AstRoot extends AbstractStatement {
    private IStatement[] statements;
    private List<IStatement> children;
    public AstRoot(final int line, final int column, final ITemplate template) {
        super(line, column, template);
        this.children = new ArrayList<IStatement>();
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        final IStatement[] statements = this.statements;
        final int n = statements.length;
        for (int i = 0; i < n; i++) {
            statements[i].execute(context);
        }
        return null;
    }

    @Override
    public final AstRoot optimize() throws Exception {
        statements = new IStatement[children.size()];
        children.toArray(statements);
        children.clear();
        children = null;
        return this;
    }

    @Override
    public final AstRoot detect() throws Exception {
        final Detection detection = this.getTemplate().getDetection();
        detection.occupy();
        final IStatement[] statements = this.statements;
        for(int i=0, n=statements.length;i<n;i++) {
            statements[i].detect();
        }
        detection.revert();
        return this;
    }

    public final AstRoot add(IStatement statement) throws Exception {
        if ((statement = statement.optimize()) == null) {
            return this;
        }
        children.add(statement);
        return this;
    }
}
