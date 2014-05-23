package org.boilit.bsl.xio;

import org.boilit.bsl.IEngine;

/**
 * @author Boilit
 * @see
 */
public abstract class AbstractResourceLoader implements IResourceLoader {
    private IEngine engine;

    @Override
    public final IEngine getEngine() {
        return engine;
    }

    @Override
    public IResourceLoader setEngine(IEngine engine) {
        this.engine = engine;
        return this;
    }

    @Override
    public final String getEncoding() {
        return engine.getInputEncoding();
    }
}
