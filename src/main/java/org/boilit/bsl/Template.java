package org.boilit.bsl;

import org.boilit.bsl.core.ExecuteContext;
import org.boilit.bsl.core.IExecute;
import org.boilit.bsl.core.Parser;
import org.boilit.bsl.encoding.EncoderFactory;
import org.boilit.bsl.encoding.IEncoder;
import org.boilit.bsl.exception.ScriptException;
import org.boilit.bsl.formatter.FormatterManager;
import org.boilit.bsl.xio.BytesPrinter;
import org.boilit.bsl.xio.CharsPrinter;
import org.boilit.bsl.xio.IPrinter;
import org.boilit.bsl.xio.IResource;
import org.boilit.bsl.xtp.ITextProcessor;

import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public final class Template {
    private final Engine engine;
    private final IResource resource;
    private final IExecute executor;
    private final FormatterManager formatterManager;

    protected Template(final Engine engine, final IResource resource, final FormatterManager formatterManager) throws Exception{
        this.engine = engine;
        this.resource = resource;
        this.formatterManager = formatterManager;
        final Parser parser = new Parser();
        parser.setTemplate(this);
        Reader reader = null;
        try {
            this.executor = parser.parse(reader = resource.openReader());
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

    public final Engine getEngine() {
        return engine;
    }

    public final IResource getResource() {
        return resource;
    }

    public final String getInputEncoding() {
        return engine.getInputEncoding();
    }

    public final String getOutputEncoding() {
        return engine.getOutputEncoding();
    }

    public final boolean isSpecifiedEncoder() {
        return engine.isSpecifiedEncoder();
    }

    public final ITextProcessor getTextProcessor() {
        return engine.getTextProcessor();
    }

    public final FormatterManager getFormatterManager() {
        return formatterManager;
    }

    public final Object execute(final Map<String, Object> model, final OutputStream outputStream) throws Exception  {
        final IEncoder encoder = EncoderFactory.getEncoder(this.getOutputEncoding(), this.isSpecifiedEncoder());
        return this.execute(model, new BytesPrinter(outputStream, encoder));
    }

    public final Object execute(final Map<String, Object> model, final Writer writer) throws Exception  {
        final IEncoder encoder = EncoderFactory.getEncoder(this.getOutputEncoding(), this.isSpecifiedEncoder());
        return this.execute(model, new CharsPrinter(writer, encoder));
    }

    public final Object execute(final Map<String, Object> model, final IPrinter printer) throws Exception {
        return this.execute(new ExecuteContext(model, printer));
    }

    public final Object execute(final ExecuteContext context) throws Exception {
        Object value = null;
        try{
            this.executor.execute(context);
            context.getPrinter().flush();
            context.clear();
        } catch (ScriptException e) {
            throw e.toScriptException();
        }
        return value;
    }
}
