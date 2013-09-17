package org.boilit.bsl.core;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractExpression extends AbstractStatement {
    public AbstractExpression(final int line, final int column) {
        super(line, column);
    }

    @Override
    public AbstractExpression optimize() throws Exception {
        return this;
    }
}
