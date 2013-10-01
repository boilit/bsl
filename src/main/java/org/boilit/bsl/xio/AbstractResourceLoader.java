package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractResourceLoader implements IResourceLoader {
    private String encoding;

    @Override
    public final String getEncoding() {
        return encoding;
    }

    @Override
    public final IResourceLoader setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }
}
