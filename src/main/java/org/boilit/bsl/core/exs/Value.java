package org.boilit.bsl.core.exs;

import org.boilit.bsl.core.AbstractStructure;
import org.boilit.bsl.core.ExecuteContext;

/**
 * @author Boilit
 * @see
 */
public final class Value extends AbstractStructure {
    private final Object value;

    public Value(final int line, final int column, final Object value) {
        super(line, column);
        this.value = value;
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        return value;
    }
}
