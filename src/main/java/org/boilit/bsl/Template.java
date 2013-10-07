package org.boilit.bsl;

import org.boilit.bsl.core.*;
import org.boilit.bsl.exception.ScriptException;
import org.boilit.bsl.xio.IResource;

import java.io.Reader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Boilit
 * @see
 */
public final class Template extends AbstractTemplate {
    private final IResource resource;
    private final IExecute executor;
    private final ConcurrentMap<String, Fragment> fragments;

    protected Template(final IEngine engine, final IResource resource) throws Exception{
        super(engine);
        this.resource = resource;
        this.fragments = new ConcurrentHashMap<String, Fragment>();
        final Parser parser = new Parser();
        parser.setTemplate(this);
        Reader reader = null;
        try {
            this.executor = parser.parse(reader = resource.openReader()).detect();
        } catch (ScriptException e) {
            throw e.toScriptException();
        } catch (Exception e) {
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    public final ITemplate getTemplate() {
        return this;
    }

    public final IResource getResource() {
        return resource;
    }

    @Override
    public final ConcurrentMap<String, Fragment> getFragments() {
        return fragments;
    }

    public final Object execute(final Context context) throws Exception {
        Object value = null;
        try{
            this.executor.execute(context);
            context.getPrinter().flush();
            context.destroy();
        } catch (ScriptException e) {
            throw e.toScriptException();
        }
        return value;
    }
}
