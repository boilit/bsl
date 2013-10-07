package org.boilit.bsl.core.dxs;

import org.boilit.bsl.IEngine;
import org.boilit.bsl.IBreakPointer;
import org.boilit.bsl.ITemplate;
import org.boilit.bsl.core.AbstractDirective;
import org.boilit.bsl.Context;

/**
 * @author Boilit
 * @see
 */
public final class BreakPoint extends AbstractDirective {
    private final IEngine engine;
    private final IBreakPointer breakPointer;
    public BreakPoint(final int line, final int column, final ITemplate template) {
        super(line, column, template);
        this.engine = template.getEngine();
        this.breakPointer = engine.getBreakPointer();
    }

    @Override
    public final Object execute(final Context context) throws Exception {
        if(breakPointer != null) {
            breakPointer.watch(engine, this.getTemplate(), context);
        }
        return null;
    }

    @Override
    public final BreakPoint detect() throws Exception {
        return this;
    }
}
