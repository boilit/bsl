package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public final class ClassPathResourceLoader extends AbstractLoader {

    public ClassPathResourceLoader(final String encoding) {
        super(encoding);
    }

    @Override
    public final IResource getResource(final String name) {
        return new ClassPathResource(this, name);
    }
}
