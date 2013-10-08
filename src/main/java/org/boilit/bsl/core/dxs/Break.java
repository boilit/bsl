package org.boilit.bsl.core.dxs;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.Context;

/**
 * @author Boilit
 * @see
 */
public final class Break extends AbstractDirective {

    public Break(final int line, final int column, final ITemplate template) {
        super(line, column, template);
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        context.setControl(Context.CONTROL_BREAK);
        return null;
    }

    @Override
    public final Break detect() throws Exception {
        return this;
    }
}
