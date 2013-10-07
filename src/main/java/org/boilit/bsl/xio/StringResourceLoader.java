package org.boilit.bsl.xio;

import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class StringResourceLoader extends AbstractResourceLoader {
    private StringResource resource;

    public StringResourceLoader() {
        this.resource = new StringResource();
        this.resource.setLoader(this);
    }
    @Override
    public final IResource getResource(final String name) {
        this.resource.setName(name);
        return this.resource;
    }

    public final Map<String, String> getResources() {
        return resource.getResources();
    }
}
