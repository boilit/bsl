package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public final class FileResourceLoader extends AbstractResourceLoader {

    public FileResourceLoader(final String encoding) {
        super(encoding);
    }

    @Override
    public final IResource getResource(final String name) {
        return new FileResource(this, name);
    }
}
