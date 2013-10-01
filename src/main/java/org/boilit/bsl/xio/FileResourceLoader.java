package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public final class FileResourceLoader extends AbstractResourceLoader {

    @Override
    public final IResource getResource(final String name) {
        final IResource resource = new FileResource();
        resource.setLoader(this);
        resource.setName(name);
        return resource;
    }
}
