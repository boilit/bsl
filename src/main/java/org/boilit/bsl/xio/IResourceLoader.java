package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public interface IResourceLoader {

    public String getEncoding();

    public IResource getResource(String name);
}
