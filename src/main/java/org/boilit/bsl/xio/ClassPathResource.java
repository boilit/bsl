package org.boilit.bsl.xio;

import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Boilit
 * @see
 */
public final class ClassPathResource extends AbstractResource {
    @Override
    public final Reader openReader() throws Exception {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if(this.getEncoding() == null || this.getEncoding().trim().length() == 0) {
            return new InputStreamReader(classLoader.getResourceAsStream(this.getName()));
        }
        return new InputStreamReader(classLoader.getResourceAsStream(this.getName()), this.getEncoding());
    }
}
