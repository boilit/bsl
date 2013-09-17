package org.boilit.bsl.xio;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Boilit
 * @see
 */
public final class ClassPathResource extends AbstractResource {

    public ClassPathResource(final IResourceLoader loader, final String path) {
        super(loader, path);
        if (path == null) {
            throw new IllegalArgumentException("Argument[String path] is null!");
        }
    }

    @Override
    public final Reader openReader() throws Exception {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return new InputStreamReader(classLoader.getResourceAsStream(this.getName()), this.getEncoding());
    }
}
