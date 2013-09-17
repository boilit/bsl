package org.boilit.bsl.core;

/**
 * @author Boilit
 * @see
 */
public interface IStatement extends IExecute {

    public int getLine();

    public int getColumn();

    public IStatement optimize() throws Exception;
}
