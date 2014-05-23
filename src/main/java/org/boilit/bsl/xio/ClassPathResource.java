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
        final ClassLoader classLoader = this.getLoader().getEngine().getClassLoader();
        if(this.getEncoding() == null || this.getEncoding().trim().length() == 0) {
            return new InputStreamReader(classLoader.getResourceAsStream(this.getName()));
        }
        return new InputStreamReader(classLoader.getResource(this.getName()).openStream(), this.getEncoding());
    }
}
