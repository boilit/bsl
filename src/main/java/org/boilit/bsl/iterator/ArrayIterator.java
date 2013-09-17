package org.boilit.bsl.iterator;

import java.util.Iterator;

/**
 * @author Boilit
 * @see
 */
public final class ArrayIterator<E> implements Iterator<E> {
    private int index = 0;
    private E[] elements;

    public ArrayIterator(E[] elements) {
        this.elements = elements;
    }

    public final boolean hasNext() {
        if (elements == null) {
            return false;
        }
        return index < elements.length;
    }

    public final E next() {
        return elements[index++];
    }

    @Override
    public final void remove() {
    }
}
