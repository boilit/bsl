package org.boilit.bsl.core.dxs;

import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.Context;
import org.boilit.bsl.core.IStatement;

/**
 * @author Boilit
 * @see
 */
public final class Next extends AbstractDirective {

    public Next(final int line, final int column, final ITemplate template) {
        super(line, column, template);
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        context.setControl(Context.CONTROL_NEXT);
        return null;
    }

    @Override
    public final AbstractDirective optimize() throws Exception {
        return this;
    }

    @Override
    public final IStatement detect() throws Exception {
        return this;
    }
}
