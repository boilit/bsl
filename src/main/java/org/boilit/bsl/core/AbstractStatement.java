package org.boilit.bsl.core;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractStatement implements IStatement {
    private final int line;
    private final int column;

    public AbstractStatement(final int line, final int column) {
        this.line = line;
        this.column = column;
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
}
