package org.boilit.cup;

/**
 * @author Boilit
 * @see
 */
public final class ArrayStack<T> implements Stack<T> {
    private int size;
    private Object[] elements;

    public ArrayStack() {
        this(8);
    }

    public ArrayStack(int initialCapacity) {
        size = 0;
        elements = new Object[initialCapacity];
    }

    @Override
    public T push(T element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
        return element;
    }

    public T peek() {
        return peek(0);
    }

    public T peek(int offset) {
        return (T) elements[size - offset - 1];
    }

    @Override
    public T pop() {
        if (size == 0) {
            return null;
        }
        T element = (T) elements[--size];
        elements[size] = null;
        return element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    public void clear() {
        for (int i = size - 1; i >= 0; i--) {
            elements[i] = null;
        }
        size = 0;
    }

    protected void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = ((elements.length * 3) >> 1) + 1;
            Object[] oldData = elements;
            elements = new Object[newCapacity < minCapacity ? minCapacity : newCapacity];
            System.arraycopy(oldData, 0, elements, 0, size);
        }
    }
}
