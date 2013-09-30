package org.boilit.bsl.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class ObjectIterator implements Iterator {
    private Iterator iterator;

    public ObjectIterator(Object object) {
        if (object == null) {
            this.iterator = new EmptyIterator();
        } else if (object instanceof List) {
            this.iterator = ((List) object).iterator();
        } else if (object instanceof Map) {
            this.iterator = ((Map) object).entrySet().iterator();
        } else if (object instanceof Object[]) {
            this.iterator = new ArrayIterator((Object[]) object);
        } else if (object instanceof Boolean) {
            this.iterator = new BoolIterator((Boolean) object);
        } else if (object instanceof Number) {
            this.iterator = new DigitIterator(((Number) object).doubleValue());
        } else {
            this.iterator = new EmptyIterator();
        }
    }

    @Override
    public final boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public final Object next() {
        return this.iterator.next();
    }

    @Override
    public final void remove() {
        this.iterator.remove();
    }
}
