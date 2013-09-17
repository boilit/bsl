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

    public FileResource(final IResourceLoader loader, final String file) {
        super(loader, file.replace(File.separatorChar, '/'));
        if (file == null) {
            throw new IllegalArgumentException("Argument[String file] is null!");
        }
    }

    @Override
    public final Reader openReader() throws Exception {
        final String file = this.getName().replace('/', File.separatorChar);
        return new InputStreamReader(new FileInputStream(file), this.getEncoding());
    }
}
