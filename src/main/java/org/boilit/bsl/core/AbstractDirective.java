package org.boilit.bsl.core;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractDirective extends AbstractStatement {
    public AbstractDirective(final int line, final int column) {
        super(line, column);
    }

    @Override
    public AbstractDirective optimize() throws Exception {
        return this;
    }
}
