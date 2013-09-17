package org.boilit.bsl.core.eoo;

import org.boilit.bsl.core.AbstractOneselfOperator;
import org.boilit.bsl.core.Operation;

/**
 * @author Boilit
 * @see
 */
public final class NumSub1 extends AbstractOneselfOperator {
    public NumSub1(final int line, final int column, final String label, final boolean previous) {
        super(line, column, label, previous);
    }

    @Override
    protected final Object executeFragment(final Object value) throws Exception {
        return Operation.doNumSub1(value);
    }
}
