package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractResource implements IResource {
    private final IResourceLoader loader;
    private final String name;

    public AbstractResource(final IResourceLoader loader, final String name) {
        if (loader == null) {
            throw new IllegalArgumentException("Argument[ILoader loader] is null!");
        }
        this.loader = loader;
        this.name = name;
    }

    @Override
    public final IResourceLoader getLoader() {
        return loader;
    }

    @Override
    public final String getEncoding() {
        return loader.getEncoding();
    }

    @Override
    public final String getName() {
        return name;
    }
}
