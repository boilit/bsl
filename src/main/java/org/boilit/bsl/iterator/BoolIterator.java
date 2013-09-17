package org.boilit.bsl.iterator;

import java.util.Iterator;

/**
 * @author Boilit
 * @see
 */
public final class BoolIterator implements Iterator {
    private boolean bool;

    public BoolIterator(boolean bool) {
        this.bool = bool;
    }

    public final boolean hasNext() {
        return bool;
    }

    public final Boolean next() {
        return bool;
    }

    @Override
    public final void remove() {
    }
}
