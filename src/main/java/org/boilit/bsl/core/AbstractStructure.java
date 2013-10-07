package org.boilit.bsl.core;

import org.boilit.bsl.ITemplate;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractStructure extends AbstractExpression {
    public AbstractStructure(final int line, final int column, final ITemplate template) {
        super(line, column, template);
    }
}
