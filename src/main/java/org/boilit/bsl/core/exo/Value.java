package org.boilit.bsl.core.exo;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractExpression;
import org.boilit.bsl.core.AbstractStructure;
import org.boilit.bsl.Context;

/**
 * @author Boilit
 * @see
 */
public final class Value extends AbstractStructure {
    private final Object value;

    public Value(final int line, final int column, final Object value, final ITemplate template) {
        super(line, column, template);
        this.value = value;
    }

    @Override
    public final AbstractExpression detect() throws Exception {
        return this;
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        return value;
    }
}
