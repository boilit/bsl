package org.boilit.bsl.xio;

import org.boilit.bsl.exception.ScriptException;

import java.io.*;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class StringResource extends AbstractResource {
    private Map<String, String> resources;

    public StringResource(final IResourceLoader loader, final String string) {
        super(loader, string);
        if (string == null) {
            throw new IllegalArgumentException("Argument[String string] is null!");
        }
    }

    @Override
    public final Reader openReader() throws Exception {
        if (resources == null) {
            throw new ScriptException("StringResource's resources was not set!");
        }
        final String string = resources.get(this.getName());
        if (string == null) {
            throw new ScriptException("StringResource's resource[" + this.getName() + "] was not exist!");
        }
        return new StringReader(string);
    }

    public final Map<String, String> getResources() {
        return resources;
    }

    public final void setResources(Map<String, String> resources) {
        this.resources = resources;
    }
}
