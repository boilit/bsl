package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public final class StringResourceLoader extends AbstractResourceLoader {
    @Override
    public final IResource getResource(final String name) {
        final IResource resource = new StringResource();
        resource.setLoader(this);
        resource.setName(name);
        return resource;
    }
}
