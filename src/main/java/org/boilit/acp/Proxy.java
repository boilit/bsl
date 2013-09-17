package org.boilit.acp;

/**
 * @author Boilit
 * @see
 */
public abstract class Proxy {

    public abstract Object invoke(final Object object, final Object[] parameters) throws Exception;
}
