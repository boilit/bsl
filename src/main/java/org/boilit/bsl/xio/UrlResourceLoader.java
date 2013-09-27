package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public final class UrlResourceLoader extends AbstractResourceLoader {

    public UrlResourceLoader(final String encoding) {
        super(encoding);
    }

    @Override
    public final IResource getResource(final String name) {
        return new UrlResource(this, name);
    }
}
