package org.boilit.bsl.xio;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractLoader implements IResourceLoader {
    private final String encoding;

    public AbstractLoader(final String encoding) {
        if (encoding == null) {
            throw new IllegalArgumentException("Argument[String encoding] is null!");
        }
        this.encoding = encoding;
    }

    @Override
    public final String getEncoding() {
        return encoding;
    }
}
