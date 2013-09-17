package org.boilit.bsl.iterator;

import java.util.Iterator;

/**
 * @author Boilit
 * @see
 */
public final class EmptyIterator implements Iterator {
    @Override
    public final boolean hasNext() {
        return false;
    }

    @Override
    public final Object next() {
        return null;
    }

    @Override
    public final void remove() {
    }
}
