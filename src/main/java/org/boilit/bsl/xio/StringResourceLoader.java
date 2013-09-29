package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public final class StringResourceLoader extends AbstractResourceLoader {

    public StringResourceLoader(final String encoding) {
        super(encoding);
    }

    @Override
    public final IResource getResource(final String name) {
        return new StringResource(this, name);
    }
}
