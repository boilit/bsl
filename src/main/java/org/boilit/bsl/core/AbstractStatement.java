package org.boilit.bsl.core;

import org.boilit.bsl.ITemplate;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractStatement implements IStatement {
    private final int line;
    private final int column;
    private final ITemplate template;

    public AbstractStatement(final int line, final int column, final ITemplate template) {
        this.line = line;
        this.column = column;
        this.template = template;
    }

    @Override
    public AbstractStatement optimize() throws Exception {
        return this;
    }

    public final int getLine() {
        return line;
    }

    public final int getColumn() {
        return column;
    }

    public final ITemplate getTemplate() {
        return template;
    }
}
