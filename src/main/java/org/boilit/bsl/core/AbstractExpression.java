package org.boilit.bsl.core;

import org.boilit.bsl.ITemplate;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractExpression extends AbstractStatement {
    public AbstractExpression(final int line, final int column, final ITemplate template) {
        super(line, column, template);
    }

    @Override
    public AbstractExpression optimize() throws Exception {
        return this;
    }
}
