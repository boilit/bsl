package org.boilit.bsl.core;

import org.boilit.bsl.ITemplate;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractDirective extends AbstractStatement {
    public AbstractDirective(final int line, final int column, final ITemplate template) {
        super(line, column, template);
    }

    @Override
    public AbstractDirective optimize() throws Exception {
        return this;
    }
}
