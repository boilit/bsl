package org.boilit.bsl.core.dxs;

import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.core.ExecuteContext;

/**
 * @author Boilit
 * @see
 */
public final class Break extends AbstractDirective {

    public Break(final int line, final int column) {
        super(line, column);
    }

    @Override
    public final Object execute(final ExecuteContext context) throws Exception {
        return context.setControl(ExecuteContext.CONTROL_BREAK);
    }
}
