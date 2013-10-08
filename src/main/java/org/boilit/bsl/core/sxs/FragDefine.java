package org.boilit.bsl.core.sxs;

import org.boilit.bsl.Context;
import org.boilit.bsl.Detection;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractStatement;
import org.boilit.bsl.core.IStatement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class FragDefine extends AbstractStatement {
    private final String label;
    private IStatement[] statements;
    private List<IStatement> children;

    public FragDefine(final int line, final int column, final String label, final ITemplate template) {
        super(line, column, template);
        this.label = label;
        this.children = new ArrayList<IStatement>();
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        return null;
    }

    @Override
    public final FragDefine optimize() throws Exception {
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
    public final FragDefine detect() throws Exception {
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

    public final FragDefine add(IStatement statement) throws Exception {
        if ((statement = statement.optimize()) == null) {
            return this;
        }
        this.children.add(statement);
        return this;
    }

    public final String getLabel() {
        return label;
    }

    public final IStatement[] getStatements() {
        return statements;
    }
}
