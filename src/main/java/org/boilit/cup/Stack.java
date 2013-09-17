package org.boilit.cup;

/**
 * @author Boilit
 * @see
 */
public interface Stack<E> {

    public E push(E item);

    public E peek();
    
    public E peek(int offset);

    public E pop();
    
    public int size();

    public boolean empty();

    public void clear();
}
