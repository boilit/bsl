package org.boilit.bsl.xio;

import java.io.Reader;

/**
 * @author Boilit
 * @see
 */
public interface IResource {

    public IResourceLoader getLoader();

    public IResource setLoader(IResourceLoader loader);

    public String getName();

    public IResource setName(String name);

    public String getEncoding();

    public Reader openReader() throws Exception;
}
