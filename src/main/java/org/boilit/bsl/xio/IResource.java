package org.boilit.bsl.xio;

import java.io.Reader;

/**
 * @author Boilit
 * @see
 */
public interface IResource {

    public IResourceLoader getLoader();

    public String getEncoding();

    public String getName();

    public Reader openReader() throws Exception;
}
