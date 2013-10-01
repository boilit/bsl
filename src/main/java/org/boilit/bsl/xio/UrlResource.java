package org.boilit.bsl.xio;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * @author Boilit
 * @see
 */
public final class UrlResource extends AbstractResource {
    @Override
    public final Reader openReader() throws Exception {
        if(this.getEncoding() == null || this.getEncoding().trim().length() == 0) {
            return new InputStreamReader(new URL(this.getName()).openStream());
        }
        return new InputStreamReader(new URL(this.getName()).openStream(), this.getEncoding());
    }
}
