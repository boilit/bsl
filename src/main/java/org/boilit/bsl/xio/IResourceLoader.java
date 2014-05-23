package org.boilit.bsl.xio;

import org.boilit.bsl.IEngine;

/**
 * @author Boilit
 * @see
 */
public interface IResourceLoader {

    public IEngine getEngine();

    public IResourceLoader setEngine(IEngine engine);

    public String getEncoding();

    public IResource getResource(String name);
}
