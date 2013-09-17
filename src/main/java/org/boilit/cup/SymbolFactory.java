package org.boilit.cup;

/**
 * Creates the Symbols interface, which CUP uses as default
 * <p/>
 * Interface SymbolFactory: interface for creating new symbols
 * You can also use this interface for your own callback hooks
 * Declare Your own factory methods for creation of Objects in Your scanner!
 *
 * @author Michael Petter
 * @version last updated 27-03-2006
 */

/* *************************************************
 ***************************************************/
public interface SymbolFactory {
    // Factory methods

    /**
     * Construction with left/right propagation switched on
     */
    public Symbol newSymbol(String name, int id, Symbol left, Symbol right, Object value);

    public Symbol newSymbol(String name, int id, Symbol left, Symbol right);

    /**
     * Construction with left/right propagation switched off
     */
    public Symbol newSymbol(String name, int id, Object value);

    public Symbol newSymbol(String name, int id);

    /**
     * Construction of start symbol
     */
    public Symbol startSymbol(String name, int id, int state);
}
