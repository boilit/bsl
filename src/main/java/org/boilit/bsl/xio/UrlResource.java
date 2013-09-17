package org.boilit.bsl.xio;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * @author Boilit
 * @see
 */
public final class UrlResource extends AbstractResource {

    public UrlResource(final IResourceLoader loader, final String url) {
        super(loader, url);
        if (url == null) {
            throw new IllegalArgumentException("Argument[String url] is null!");
        }
    }

    @Override
    public final Reader openReader() throws Exception {
        return new InputStreamReader(new URL(this.getName()).openStream(), this.getEncoding());
    }
}
