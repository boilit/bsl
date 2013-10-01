package org.boilit.bsl.xio;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Boilit
 * @see
 */
public final class FileResource extends AbstractResource {
    @Override
    public final Reader openReader() throws Exception {
        final String file = this.getName().replace('/', File.separatorChar);
        if(this.getEncoding() == null || this.getEncoding().trim().length() == 0) {
            return new InputStreamReader(new FileInputStream(file));
        }
        return new InputStreamReader(new FileInputStream(file), this.getEncoding());
    }
}
