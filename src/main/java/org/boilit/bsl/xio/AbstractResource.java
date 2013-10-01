package org.boilit.bsl.xio;

import java.io.File;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractResource implements IResource {
    private IResourceLoader loader;
    private String name;

    @Override
    public final IResourceLoader getLoader() {
        return loader;
    }

    @Override
    public final IResource setLoader(IResourceLoader loader) {
        this.loader = loader;
        return this;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final IResource setName(String name) {
        this.name = name.replace(File.separatorChar, '/');
        return this;
    }

    @Override
    public final String getEncoding() {
        return loader.getEncoding();
    }
}
